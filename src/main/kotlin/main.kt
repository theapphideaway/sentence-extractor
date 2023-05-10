import java.util.*
import kotlin.collections.HashMap

fun main(args: Array<String>){
    val article  = "The entertainment world is bidding farewell to MTV News. The network's heyday ended years ago but, for a time, it was the go-to source for interviews with leading artists and newsmakers, from Madonna to Prince.\n" +

            "The shutdown of MTV News comes as Paramount Global, the parent company of CBS, Nickelodeon, Comedy Central and Showtime announced today that it is laying off some 25% of its staff.\n" +

            "In addition to reports of a soft ad market, Paramount Global is doing considerable restructuring. Earlier this year, Showtime merged with MTV Entertainment Studios.\n" +

            "In an email to staff obtained by NPR, Chris McCarthy, president and CEO of Showtime/MTV Entertainment Studios and Paramount Media Networks, explained the decision-making behind the cuts. While touting the \"incredible track record of hits\" such as Yellowstone, South Park, and Yellowjackets, McCarthy wrote, \"despite this success in streaming, we continue to feel pressure from broader economic headwinds like many of our peers. To address this, our senior leaders in coordination with HR have been working together over the past few months to determine the optimal organization for the current and future needs of our business.\"\n" +

            "\"This is a very sad day for a lot of friends and colleagues,\" wrote MTV News' Josh Horowitz on Instagram, \"Many great people lost their jobs. I was hired by MTV News 17 years ago. I'm so honored to have been a small part of its history. Wishing the best for the best in the business.\"\n" +

            "The news comes on the heels of a disappointing first quarter earnings report for the corporation. During the earnings call this week, Paramount Global CEO Bob Bakish said the corporation was \"navigating a challenging and uncertain macroeconomic environment, and you see the impact of that on our financials, as the combination of peak streaming investment intersects with cyclical ad softness.\"\n" +

            "A few days ago, MTV announced it was scaling back its annual awards show in the face of an ongoing writer's strike."

    getWordsAndSentences(article).forEach { entity->
        println("Word: ${entity.word}")
        println("Sentence: ${entity.sentence.replace('\n',' ')}")
        println("")
    }

}

fun getWordsAndSentences(article:String): ArrayList<VocabEntity>{
    val vocabEntities =  ArrayList<VocabEntity>()
    val allWords =  article.replace(",","")
        .replace(".","")
        .split(" ")
    val wordBank = HashMap<String, Int>()
    allWords.forEach { word ->
        if (wordBank.isEmpty()) {
            wordBank[word.lowercase(Locale.getDefault())] = 1
        } else {
            if (wordBank.containsKey(word)) {
                val value = wordBank[word.lowercase(Locale.getDefault())+"%0A"]
                if (value != null) {
                    wordBank[word.lowercase(Locale.getDefault())] = value + 1
                }
            } else {
                wordBank[word.lowercase(Locale.getDefault())] = 1
            }
        }
    }
    val sortedWords = wordBank.toList().sortedBy { (_, value) -> value}.reversed().toMap()
    val topWords = sortedWords.keys.take(5)

    val topWordsList = topWords.toList()


    val sentences = ArrayList<String>()

    var tempSentence = ""
    article.forEach {letter ->
        if(letter != '.'){
            tempSentence += letter
        } else{
            tempSentence += "."
            sentences.add(tempSentence)
            tempSentence = ""
        }
    }
    sentences.forEach { sentence ->
        topWords.forEach lit@ { word ->
            if (sentence.contains(" $word ")){
                vocabEntities.add(VocabEntity(word, sentence))
                return@lit
            }
        }
    }

    return vocabEntities
}

data class VocabEntity(val word: String, val sentence: String)