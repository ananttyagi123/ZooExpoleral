package com.example.zooexpoleral

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

data class AnimalQuestion(
    val question: String,
    val options: List<String>,
    val answerIndex: Int,
    val category: String
)

class GuessAnimalActivity : BaseActivity() {

    private lateinit var questions: List<AnimalQuestion>
    private var currentIndex = 0
    private var score = 0

    private lateinit var questionText: TextView
    private lateinit var scoreText: TextView
    private lateinit var questionCounter: TextView
    private lateinit var optionButtons: List<Button>
    private lateinit var quizLayout: View
    private lateinit var categoryLayout: View

    private lateinit var selectedCategory: String
    private lateinit var filteredQuestions: List<AnimalQuestion>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_animal)

        quizLayout = findViewById(R.id.quizLayout)
        categoryLayout = findViewById(R.id.categoryLayout)
        questionText = findViewById(R.id.questionTextView)
        scoreText = findViewById(R.id.scoreTextView)
        questionCounter = findViewById(R.id.questionCounter)

        optionButtons = listOf(
            findViewById(R.id.optionButton1),
            findViewById(R.id.optionButton2),
            findViewById(R.id.optionButton3),
            findViewById(R.id.optionButton4)
        )

        findViewById<Button>(R.id.btnMammals).setOnClickListener { startQuiz("Mammals") }
        findViewById<Button>(R.id.btnBirds).setOnClickListener { startQuiz("Birds") }
        findViewById<Button>(R.id.btnReptiles).setOnClickListener { startQuiz("Reptiles") }
        findViewById<Button>(R.id.btnAll).setOnClickListener { startQuiz("All") }

        findViewById<Button>(R.id.prevButton).setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                showQuestion()
            }
        }

        findViewById<Button>(R.id.nextButton).setOnClickListener {
            if (currentIndex < filteredQuestions.size - 1) {
                currentIndex++
                showQuestion()
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("USER_SCORE", score)
                intent.putExtra("TOTAL_QUESTIONS", filteredQuestions.size)
                startActivity(intent)
                finish()
            }
        }

        questions = generateQuestions(this)
    }

    private fun startQuiz(category: String) {
        selectedCategory = category
        filteredQuestions = if (category == "All") questions.shuffled().take(50)
        else questions.filter { it.category == category }.shuffled().take(50)

        currentIndex = 0
        score = 0
        categoryLayout.visibility = View.GONE
        quizLayout.visibility = View.VISIBLE
        showQuestion()
    }
    private fun showQuestion() {
        val question = filteredQuestions[currentIndex]
        questionText.text = question.question
        questionCounter.text = "Question: ${currentIndex + 1}/${filteredQuestions.size}"
        scoreText.text = "Score: $score"

        for (i in 0..3) {
            optionButtons[i].text = question.options[i]
            optionButtons[i].setBackgroundColor(getColor(android.R.color.darker_gray))
            optionButtons[i].isEnabled = true
            optionButtons[i].setOnClickListener {
                val isCorrect = i == question.answerIndex
                if (isCorrect) {
                    score++
                    it.setBackgroundColor(getColor(android.R.color.holo_green_light))
                } else {
                    it.setBackgroundColor(getColor(android.R.color.holo_red_light))
                }
                disableOptions()
                scoreText.text = "Score: $score"
            }
        }
    }

    private fun disableOptions() {
        optionButtons.forEach { it.isEnabled = false }
    }

    private fun generateQuestions(context: Context): List<AnimalQuestion> {
        val qList = mutableListOf<AnimalQuestion>()


        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_king_of_jungle),
                listOf(
                    context.getString(R.string.opt_lion),
                    context.getString(R.string.opt_tiger),
                    context.getString(R.string.opt_elephant),
                    context.getString(R.string.opt_leopard)
                ),
                0, "Mammals"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_mammal_lays_eggs),
                listOf(
                    context.getString(R.string.opt_platypus),
                    context.getString(R.string.opt_kangaroo),
                    context.getString(R.string.opt_whale),
                    context.getString(R.string.opt_cat)
                ),
                0, "Mammals"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_largest_land_animal),
                listOf(
                    context.getString(R.string.opt_rhino),
                    context.getString(R.string.opt_giraffe),
                    context.getString(R.string.opt_elephant),
                    context.getString(R.string.opt_buffalo)
                ),
                2, "Mammals"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_powerful_bite),
                listOf(
                    context.getString(R.string.opt_hyena),
                    context.getString(R.string.opt_lion),
                    context.getString(R.string.opt_tiger),
                    context.getString(R.string.opt_hippopotamus)
                ),
                3, "Mammals"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_echolocation),
                listOf(
                    context.getString(R.string.opt_dog),
                    context.getString(R.string.opt_bat),
                    context.getString(R.string.opt_horse),
                    context.getString(R.string.opt_cat)
                ),
                1, "Mammals"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_fastest_mammal),
                listOf(
                    context.getString(R.string.opt_tiger),
                    context.getString(R.string.opt_leopard),
                    context.getString(R.string.opt_cheetah),
                    context.getString(R.string.opt_deer)
                ),
                2, "Mammals"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_group_lions),
                listOf(
                    context.getString(R.string.opt_pack),
                    context.getString(R.string.opt_pride),
                    context.getString(R.string.opt_herd),
                    context.getString(R.string.opt_swarm)
                ),
                1, "Mammals"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_longest_gestation),
                listOf(
                    context.getString(R.string.opt_whale),
                    context.getString(R.string.opt_elephant),
                    context.getString(R.string.opt_giraffe),
                    context.getString(R.string.opt_camel)
                ),
                1, "Mammals"
            )
        )

        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_bird_mimic_speech),
                listOf(
                    context.getString(R.string.opt_parrot),
                    context.getString(R.string.opt_sparrow),
                    context.getString(R.string.opt_crow),
                    context.getString(R.string.opt_eagle)
                ),
                0, "Birds"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_fastest_flying_bird),
                listOf(
                    context.getString(R.string.opt_falcon),
                    context.getString(R.string.opt_eagle),
                    context.getString(R.string.opt_hawk),
                    context.getString(R.string.opt_vulture)
                ),
                0, "Birds"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_tail_feathers),
                listOf(
                    context.getString(R.string.opt_peacock),
                    context.getString(R.string.opt_flamingo),
                    context.getString(R.string.opt_ostrich),
                    context.getString(R.string.opt_duck)
                ),
                0, "Birds"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_largest_egg),
                listOf(
                    context.getString(R.string.opt_penguin),
                    context.getString(R.string.opt_ostrich),
                    context.getString(R.string.opt_emu),
                    context.getString(R.string.opt_eagle)
                ),
                1, "Birds"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_group_owls),
                listOf(
                    context.getString(R.string.opt_parliament),
                    context.getString(R.string.opt_flock),
                    context.getString(R.string.opt_herd),
                    context.getString(R.string.opt_mob)
                ),
                0, "Birds"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_bird_cannot_fly),
                listOf(
                    context.getString(R.string.opt_penguin),
                    context.getString(R.string.opt_pigeon),
                    context.getString(R.string.opt_crow),
                    context.getString(R.string.opt_swan)
                ),
                0, "Birds"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_national_bird_india),
                listOf(
                    context.getString(R.string.opt_parrot),
                    context.getString(R.string.opt_peacock),
                    context.getString(R.string.opt_swan),
                    context.getString(R.string.opt_kingfisher)
                ),
                1, "Birds"
            )
        )

        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_shell_reptile),
                listOf(
                    context.getString(R.string.opt_snake),
                    context.getString(R.string.opt_lizard),
                    context.getString(R.string.opt_turtle),
                    context.getString(R.string.opt_crocodile)
                ),
                2, "Reptiles"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_color_change_reptile),
                listOf(
                    context.getString(R.string.opt_gecko),
                    context.getString(R.string.opt_chameleon),
                    context.getString(R.string.opt_snake),
                    context.getString(R.string.opt_iguana)
                ),
                1, "Reptiles"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_largest_reptile),
                listOf(
                    context.getString(R.string.opt_komodo),
                    context.getString(R.string.opt_alligator),
                    context.getString(R.string.opt_crocodile),
                    context.getString(R.string.opt_anaconda)
                ),
                2, "Reptiles"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_tail_detach),
                listOf(
                    context.getString(R.string.opt_snake),
                    context.getString(R.string.opt_lizard),
                    context.getString(R.string.opt_chameleon),
                    context.getString(R.string.opt_tortoise)
                ),
                1, "Reptiles"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_most_venomous_snake),
                listOf(
                    context.getString(R.string.opt_cobra),
                    context.getString(R.string.opt_viper),
                    context.getString(R.string.opt_black_mamba),
                    context.getString(R.string.opt_inland_taipan)
                ),
                3, "Reptiles"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_sea_turtle_eggs),
                listOf(
                    context.getString(R.string.opt_water),
                    context.getString(R.string.opt_land),
                    context.getString(R.string.opt_coral),
                    context.getString(R.string.opt_rocks)
                ),
                1, "Reptiles"
            )
        )

        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_largest_animal),
                listOf(
                    context.getString(R.string.opt_elephant),
                    context.getString(R.string.opt_blue_whale),
                    context.getString(R.string.opt_shark),
                    context.getString(R.string.opt_giraffe)
                ),
                1, "All"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_black_white_stripes),
                listOf(
                    context.getString(R.string.opt_tiger),
                    context.getString(R.string.opt_zebra),
                    context.getString(R.string.opt_panda),
                    context.getString(R.string.opt_skunk)
                ),
                1, "All"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_tallest_animal),
                listOf(
                    context.getString(R.string.opt_elephant),
                    context.getString(R.string.opt_giraffe),
                    context.getString(R.string.opt_horse),
                    context.getString(R.string.opt_kangaroo)
                ),
                1, "All"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_play_dead),
                listOf(
                    context.getString(R.string.opt_fox),
                    context.getString(R.string.opt_opossum),
                    context.getString(R.string.opt_raccoon),
                    context.getString(R.string.opt_bat)
                ),
                1, "All"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_sleep_standing),
                listOf(
                    context.getString(R.string.opt_elephant),
                    context.getString(R.string.opt_horse),
                    context.getString(R.string.opt_dog),
                    context.getString(R.string.opt_giraffe)
                ),
                1, "All"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_no_teeth),
                listOf(
                    context.getString(R.string.opt_penguin),
                    context.getString(R.string.opt_turtle),
                    context.getString(R.string.opt_ant_eater),
                    context.getString(R.string.opt_blue_whale)
                ),
                3, "All"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_blue_blood),
                listOf(
                    context.getString(R.string.opt_octopus),
                    context.getString(R.string.opt_frog),
                    context.getString(R.string.opt_crab),
                    context.getString(R.string.opt_lizard)
                ),
                0, "All"
            )
        )
        qList.add(
            AnimalQuestion(
                context.getString(R.string.q_fingerprint),
                listOf(
                    context.getString(R.string.opt_monkey),
                    context.getString(R.string.opt_koala),
                    context.getString(R.string.opt_cat),
                    context.getString(R.string.opt_bear)
                ),
                1, "All"
            )
        )


        return qList
    }
}
