import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun main(args: Array<String>){
    val article  = "Поклонники Bravo были шокированы в понедельник новостью о том, что Ким Золчак подала на развод с Кроем Бирманном. Зрители наблюдали за их историей любви по телевидению почти десять лет, но похоже, что нам нужен Энди Коэн и камера, потому что один эксперт по правовым вопросам ожидает, что это обернется ужасом.\n" +
            " TMZ получил заявление Бирманна о разводе, и он добивается единоличной юридической и физической опеки над четырьмя детьми его и Золчака. Бывшая звезда «Настоящих домохозяек Атланты» в своем ходатайстве просила о первичной физической опеке и совместной юридической опеке. Знаменитый адвокат по разводам Шарлотта Кристиан сообщила Yahoo, что Бирманн отправляет сообщение с документом.\n" +

            "«Требование о единоличной опеке указывает на то, что это может быть неприятный развод, поскольку большинство жалоб на развод в той или иной степени требуют совместной опеки», — объясняет Кристиан. Звезды Tardy for the Party являются родителями Кроя «KJ» Джаггера-младшего, 11 лет, Каша, 10 лет, и 9-летних близнецов, Кейна и Кайи.\n" +

            "«Чтобы получить единоличную опеку, один из родителей должен доказать, что наилучшие интересы ребенка будут соблюдены, если он будет проводить с одним родителем большую часть времени, при этом этот родитель будет принимать родительские решения, а другой родитель только навещать ребенка», — продолжает Кристиан. «Вообще говоря, для присуждения единоличной опеки один из родителей должен не желать или не иметь возможности быть родителем, а единоличная опека предоставляется редко».\n" +

            "Золчак является матерью двух дочерей от предыдущих отношений, влиятельных лиц Бриэль Бирманн, 26 лет, и Арианы Бирманн, 21 год. Бывший игрок Atlanta Falcons официально усыновил их много лет назад, но на них не влияют проблемы опеки, учитывая их возраст.\n" +

            "Новость о разводе появилась сразу после сообщения TMZ о том, что у Золчака и Бирманна финансовые проблемы. По всей видимости, они задолжали 1,1 миллиона долларов в виде неуплаченных налогов, а в начале этого года их особняк в Джорджии был конфискован. В своем заявлении бывшая звезда НФЛ попросила Золчака сохранить все финансовые документы.\n" +

            "«Если налоговый счет составлен на их обоих имена, они оба будут нести ответственность за уплату налогов», — объясняет Кристиан. «Налоговое управление США имеет преимущество перед решениями о разводе или любыми соглашениями между сторонами. Налоговая служба будет преследовать их обоих по отдельности из-за налогового долга, который будет нести они оба».\n" +

            "Представитель Zolciak не прокомментировал ситуацию, когда связался с Yahoo. Ни она, ни Бирманн публично не говорили о разводе, но он, похоже, тонко упомянул об этом в Instagram.\n" +

            "Биография Бирманн в Instagram гласит: «Муж. Отец шести идеальных манчкинов. Спортсмен. Вы можете использовать Google Me.\n" +
            "Коэн, который хорошо знает бывшую пару, сказал, что был «очень удивлен» этой новостью.\n" +
            "«Я был очень удивлен. Это была не та новость, которую я когда-либо ожидал получить. Они казались такими влюбленными и просто вместе. — поделился исполнительный продюсер во вторник с Энди Коэном на SiriusXM Live. «Мне жаль слышать, что могут быть некоторые финансовые проблемы. TMZ сообщает, что они должны миллион долларов в IRS. Вчера немного написал Ким, послал свои соболезнования, потому что это грустная вещь. У них есть дети, и это было просто пара, которая казалась очень влюбленной»."

    getWordsAndSentences(article)
    .forEach { entity->
        println("Word: ${entity.word}")
        println("Sentence: ${entity.sentence.replace('\n',' ')}")
        println("")
    }

}

fun getWordsAndSentences(article:String): ArrayList<VocabEntity>{
    val vocabEntities =  ArrayList<VocabEntity>()
    val specialCharacters = Regex("[~`!@#\$%^&*()_=+\\[{\\]}\\\\|;:'\",<.>/?«»“”‘’„“‚‘‛‟❛❜❝❞❮❯❰❱]")
    val allWords =  article.replace(specialCharacters, "")
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
    val sortedWords = wordBank.toList().sortedBy { (_, value) -> value}.reversed()
    val sortedArray = ArrayList(sortedWords)
    val topWords = sortedWords.toMap().keys.take(5)

    val topWordsList = topWords.toList()

    topWordsList.forEach {
        println(it)
    }

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

    //This could probably be done better
    sentences.forEach { sentence ->
        topWordsList.forEach lit@ { word ->
            if (sentence.lowercase(Locale.getDefault()).contains(" $word ")){
                var alreadyHere = false
                if(vocabEntities.isNotEmpty()){
                    vocabEntities.forEach lit@{entity ->
                        if(entity.word == word) {
                            alreadyHere = true
                        }
                    }
                }
                if(!alreadyHere)vocabEntities.add(VocabEntity(word, sentence))
                return@lit
            }
        }
    }

    return vocabEntities
}

data class VocabEntity(val word: String, val sentence: String)