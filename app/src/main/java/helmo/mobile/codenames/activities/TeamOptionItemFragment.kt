package helmo.mobile.codenames.activities

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import helmo.mobile.codenames.databinding.FragmentTeamOptionItemBinding
import helmo.mobile.codenames.presenters.TeamOptionsArgs
import helmo.mobile.codenames.utils.FileUtils
import java.io.File
import java.io.IOException

/**
 * Fragment permettant l'affichage d'une équipe dans le menu des options.
 * Il permet d'afficher le nom et la photo de l'équipe si celle-ci en a.
 */
class TeamOptionItemFragment : Fragment() {

//    private val viewModel: TeamOptionViewModel by viewModels()

    private var _teamName: String? = null
    private var _teamPicture: String? = null

    private val teamName: String
        get() = binding.teamName.text.toString()

    private lateinit var binding: FragmentTeamOptionItemBinding

    val teamInfos: TeamOptionsArgs
        get() = TeamOptionsArgs(teamName, (if(_teamPicture != null) _teamPicture else "")!!)

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Camera permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            displayTeamPicture()
        }
        Log.d("Verif click listener", binding.teamPicture.hasOnClickListeners().toString())
    }

    private fun displayTeamPicture() {
        val photoFile = _teamPicture?.let { File(it) }
        if (photoFile != null && _teamPicture != null && _teamPicture!!.isNotEmpty()) {
            Log.d("Verif click listener", binding.teamPicture.isClickable.toString())
            // FileUtils.savePhoto(BitmapFactory.decodeFile(photoFile.absolutePath), photoFile.name, requireContext())
            if(!photoFile.isFile) {
                val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                binding.teamPicture.setImageBitmap(bitmap)
            } else
                binding.teamPicture.setImageURI(Uri.fromFile(photoFile))
        }
    }

    private fun dispatchTakePictureIntent() {
        val photoFile: File? = try {
            _teamName?.let { FileUtils.createTempImage(it, requireContext()) }
        } catch (ex: IOException) {
            null
        }

        photoFile?.let { _teamPicture = it.absolutePath }

        photoFile?.also {
            Log.d("Coucou", requireContext().packageName)
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                it
            )
            if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
            ) {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            } else if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                cameraPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } else {
                _teamName = photoURI.path
                takePictureLauncher.launch(photoURI)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(_teamName != null && _teamName!!.isNotEmpty()) {
            binding.teamName.setText(_teamName)
        }
        displayTeamPicture()

        binding.teamPicture.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamOptionItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param teamInfos     Information de l'équipe à afficher
         *
         * @return              A new instance of fragment TeamOptionItemFragment contenant les informations données.
         */
        @JvmStatic
        fun newInstance(teamInfos: TeamOptionsArgs)
             = TeamOptionItemFragment().apply {
                _teamName = teamInfos.teamName
                _teamPicture = teamInfos.teamPicturePath
            }
    }
}