package helmo.mobile.codenames.infrastructures

import helmo.mobile.codenames.model.Options

interface IOptionRepository {

    fun getOptions(): Options

    fun saveOptions(options: Options)

}