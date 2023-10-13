package helmo.mobile.codenames.model

class WordList {
    var listName: String = ""
    var words: List<String> = ArrayList()

    constructor(name: String, words: ArrayList<String>) {
        this.listName = name
        this.words = words
    }

    override fun equals(other: Any?): Boolean = (other is WordList) && listName == other.listName
}