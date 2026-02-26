package com.example.zooexpoleral

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AnimalInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_info)

        val toolbar = findViewById<Toolbar>(R.id.toolbar23)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "About Zoo Animals"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        val recyclerView = findViewById<RecyclerView>(R.id.animalRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        val lion = Animal(
            name = "Lion",
            description = "The lion is known as the king of the jungle, renowned for its majestic mane and powerful roar.",
            imageResIds = listOf(R.drawable.img_14, R.drawable.img_16, R.drawable.img_17),
            habitat = "Savanna, grasslands",
            diet = "Carnivorous",
            funFacts ="\n📌Roars can be heard from 5 miles away!🌍🔊\n📌Lions sleep up to 20 hours a day.😴 \n📌Female lions (lionesses) do most of the hunting.🏹\n📌A lion’s mane gets darker with age and good health🌟\n 📌They live in groups called prides, with up to 40 members!🦁🦁🦁"

        )

        val elephant = Animal(
            name = "Elephant",
            description = "The elephant is the largest land animal with an impressive memory and a trunk for multiple tasks.",
            imageResIds = listOf(R.drawable.img_4, R.drawable.img_18, R.drawable.img_19),
            habitat = "Forests and savannas",
            diet = "Herbivorous",
            funFacts ="\n📌They can talk using low-frequency sounds humans can’t hear🎶\n📌Elephants have the largest brain of any land animal🧠\n📌Their trunks have around 40,000 muscles💪\n📌They use their ears to cool down! 🌬️\n📌 Elephants mourn their dead, showing deep emotions💔"
        )

        val giraffe = Animal(
            name = "Giraffe",
            description = "The giraffe is the tallest terrestrial animal, recognized by its long neck and unique patchy coat.",
            imageResIds = listOf(R.drawable.img_5, R.drawable.img_20, R.drawable.img_21),
            habitat = "Savannas, grasslands, and open woodlands of Africa",
            diet = "Herbivorous, primarily feeding on leaves from acacia trees",
            funFacts = "\n📌A giraffe’s neck has the same number of bones as a human’s (7)🦴\n📌They can clean their ears with their 20-inch-long tongues! 😝\n📌Despite their height, giraffes only sleep around 30 minutes a day.😴\n📌Their heart is 2 feet long to pump blood up their neck.💓\n📌Giraffes fight by swinging their necks—this is called necking🥊"
        )

        val panda = Animal(
            name = "Panda",
            description = "The giant panda is known for its distinctive black and white fur and gentle nature.",
            imageResIds = listOf(R.drawable.img_6, R.drawable.img_22, R.drawable.img_23),
            habitat = "Mountainous bamboo forests in central China",
            diet = "Primarily bamboo, with occasional fruits and vegetation",
            funFacts = "\n📌Pandas eat for about 12–16 hours a day.🍃\n📌They can climb trees just a few months after birth.🌳\n📌Unlike other bears, pandas do not hibernate.❄️\n📌Each panda has a unique fur pattern, like a fingerprint.🖤🤍\n📌They are born pink, blind, and only the size of a stick of butter!🍼"
        )

        val zebra = Animal(
            name = "Zebra",
            description = "Zebras are famous for their striking black and white stripes that help in camouflage and social interactions.",
            imageResIds = listOf(R.drawable.img_24, R.drawable.img_7, R.drawable.img_25),
            habitat = "Grasslands and savannas of Africa",
            diet = "Herbivorous, mostly grazing on grasses",
            funFacts = "\n📌No two zebras have the same stripes—each pattern is unique!🤩\n📌Zebra stripes help keep them cool by creating air currents.🌬️\n📌A group of zebras is called a dazzle.✨\n📌They sleep standing up and only lie down if they feel safe.😴\n📌Zebra foals can walk just 20 minutes after birth!🐾"
        )

        val tiger = Animal(
            name = "Tiger",
            description = "Tigers are the largest wild cats, known for their powerful build and striking orange coat with black stripes.",
            imageResIds = listOf(R.drawable.img_26, R.drawable.img_27, R.drawable.img_28),
            habitat = "Forests, grasslands, and mangroves across Asia",
            diet = "Carnivorous, mainly preying on deer, wild boar, and buffalo",
            funFacts = "\n📌Tigers are excellent swimmers and love water!🏊\n📌Each tiger’s stripe pattern is unique.🐯\n📌They can leap over 30 feet in a single bound!🏃\n📌Tigers have night vision six times better than humans.👀\n📌A tiger’s roar can be heard from 2 miles away!🔊"
        )

        val cheetah = Animal(
            name = "Cheetah",
            description = "Cheetahs are the fastest land animals, built for speed with their lightweight bodies and long, powerful legs.",
            imageResIds = listOf(R.drawable.img_29, R.drawable.img_30, R.drawable.img_31),
            habitat = "Grasslands and open savannas of Africa and parts of Iran",
            diet = "Carnivorous, mainly hunting gazelles, impalas, and small antelopes",
            funFacts = "\n📌Cheetahs can reach speeds of 75 mph in short bursts!⚡\n📌They can accelerate from 0 to 60 mph in just 3 seconds.🚀\n📌Unlike other big cats, cheetahs can't roar, but they purr!😺\n📌Their tear-mark facial markings help reduce glare and improve focus.😎\n📌Cheetahs use their long tails for balance when running at high speeds.🏁"
        )

        val kangaroo = Animal(
            name = "Kangaroo",
            description = "Kangaroos are strong marsupials from Australia, known for their powerful hind legs and distinctive hopping movement.",
            imageResIds = listOf(R.drawable.img_32, R.drawable.img_33, R.drawable.img_34),
            habitat = "Grasslands, woodlands, and deserts of Australia",
            diet = "Herbivorous, feeding on grasses, leaves, and shrubs",
            funFacts = "\n📌Kangaroos can leap up to 25 feet in a single jump!🦘\n📌They use their tails for balance while hopping.⚖️\n📌A baby kangaroo (joey) stays in its mother’s pouch for about 6 months.👶\n📌Kangaroos can’t move backward!⬆️\n📌They are social animals and live in groups called ‘mobs’.👥"
        )

        val polarBear = Animal(
            name = "Polar Bear",
            description = "Polar bears are massive marine mammals, uniquely adapted to live in the icy Arctic region.",
            imageResIds = listOf(R.drawable.img_35, R.drawable.img_36, R.drawable.img_37),
            habitat = "Arctic ice caps and coastal regions",
            diet = "Carnivorous, mainly feeding on seals",
            funFacts = "\n📌Polar bears have black skin under their white fur to absorb heat!🖤\n📌They can swim for hours without stopping.🏊‍♂️\n📌A polar bear’s sense of smell is so strong it can detect prey nearly a mile away!👃\n📌Despite their size, they can run up to 25 mph.🏃\n📌Polar bears are the largest land carnivores in the world!🐻‍❄️"
        )

        val wolf = Animal(
            name = "Wolf",
            description = "Wolves are highly intelligent and social animals that live and hunt in packs.",
            imageResIds = listOf(R.drawable.img_38, R.drawable.img_39, R.drawable.img_40),
            habitat = "Forests, tundras, mountains, and grasslands across North America, Europe, and Asia",
            diet = "Carnivorous, hunting deer, elk, and other small mammals",
            funFacts = "\n📌Wolves have strong family bonds and communicate using howls.🐺\n📌A wolf's howl can be heard up to 10 miles away!🔊\n📌They have 42 teeth, perfect for tearing meat.🦷\n📌Wolves can run at speeds of up to 40 mph.🏃‍♂️\n📌Each wolf pack has a unique way of communicating!📢"
        )

        val flamingo = Animal(
            name = "Flamingo",
            description = "Flamingos are graceful wading birds known for their bright pink feathers and distinctive long legs.",
            imageResIds = listOf(R.drawable.img_41, R.drawable.img_42, R.drawable.img_43),
            habitat = "Wetlands, lagoons, and shallow lakes in warm regions worldwide",
            diet = "Omnivorous, feeding on algae, small fish, and invertebrates",
            funFacts = "\n📌Flamingos get their pink color from the food they eat!🦐\n📌They can stand on one leg for hours to conserve body heat.🦩\n📌A flamingo’s beak is specially designed to filter food from water.💧\n📌They are highly social and live in large flocks.👨‍👩‍👧‍👦\n📌Flamingos can fly over 35 mph!✈️"
        )

        val penguin = Animal(
            name = "Penguin",
            description = "Penguins are flightless birds adapted for life in the water, known for their tuxedo-like appearance and waddling walk.",
            imageResIds = listOf(R.drawable.img_44, R.drawable.img_45, R.drawable.img_46),
            habitat = "Antarctica, coastal regions of South America, Africa, Australia, and New Zealand",
            diet = "Carnivorous, feeding on fish, squid, and krill",
            funFacts = "\n📌Penguins can ‘fly’ underwater at speeds of 15 mph!🐧\n📌They take turns keeping their eggs warm in freezing temperatures.🥚❄️\n📌Penguins have a special gland that removes salt from seawater!🌊\n📌They communicate using unique sounds and movements.🔊\n📌Some penguins mate for life!💞"
        )

        val redPanda = Animal(
            name = "Red Panda",
            description = "Red pandas are small, tree-dwelling mammals known for their reddish fur, bushy tails, and playful nature.",
            imageResIds = listOf(R.drawable.img_47, R.drawable.img_48, R.drawable.img_49),
            habitat = "Mountain forests of the Himalayas and China",
            diet = "Omnivorous, mostly eating bamboo but also fruits, insects, and eggs",
            funFacts = "\n📌Red pandas use their tails as blankets in cold weather!❄️🦊\n📌They are more closely related to raccoons than giant pandas.🐾\n📌Red pandas love to climb trees and sleep on branches.🌲\n📌They have a false thumb, just like giant pandas, to grasp bamboo!🎋\n📌Red pandas communicate through whistles and tail signals.🔊"
        )

        val crocodile = Animal(
            name = "Crocodile",
            description = "Crocodiles are powerful reptiles with armored bodies and strong jaws, making them one of the oldest living species on Earth.",
            imageResIds = listOf(R.drawable.img_50, R.drawable.img_51, R.drawable.img_52),
            habitat = "Rivers, lakes, swamps, and coastal areas in tropical regions",
            diet = "Carnivorous, eating fish, birds, and mammals",
            funFacts = "\n📌Crocodiles can live up to 100 years!🎂\n📌They have the strongest bite force of any animal.💪\n📌Crocodiles can hold their breath underwater for over an hour!🌊\n📌They swallow stones to help digest food.🪨\n📌Baby crocodiles make chirping sounds before hatching!🐣"
        )

        val peacock = Animal(
            name = "Peacock",
            description = "Peacocks are famous for their dazzling, iridescent tail feathers, which they display in a spectacular fan-like show.",
            imageResIds = listOf(R.drawable.img_53, R.drawable.img_54, R.drawable.img_55),
            habitat = "Forests and open grasslands of India and Sri Lanka",
            diet = "Omnivorous, eating insects, plants, and small reptiles",
            funFacts = "\n📌Only male peacocks have the long, colorful tail!🦚\n📌A group of peafowls is called a ‘party’!🎉\n📌Peacocks can fly despite their long tails.✈️\n📌They can make loud calls to warn others of danger.🔊\n📌Peacocks symbolize beauty and royalty in many cultures.👑"
        )

        val meerkat = Animal(
            name = "Meerkat",
            description = "Meerkats are small, social mammals known for their upright stance and teamwork in the wild.",
            imageResIds = listOf(R.drawable.img_56, R.drawable.img_57, R.drawable.img_58),
            habitat = "Deserts and grasslands of southern Africa",
            diet = "Omnivorous, feeding on insects, small reptiles, and fruits",
            funFacts = "\n📌Meerkats stand on their hind legs to keep watch for predators!🧐\n📌They live in groups called ‘mobs’ or ‘clans’.👨‍👩‍👧‍👦\n📌Meerkats share food and help each other raise their young.🍽️\n📌They can close their ears while digging to keep sand out!🏜️\n📌Meerkats have dark patches around their eyes to reduce sun glare.😎"
        )

        val ostrich = Animal(
            name = "Ostrich",
            description = "Ostriches are the largest and fastest-running birds in the world, known for their long legs and powerful strides.",
            imageResIds = listOf(R.drawable.img_59, R.drawable.img_60, R.drawable.img_61),
            habitat = "Savannas and deserts of Africa",
            diet = "Omnivorous, eating plants, seeds, and small animals",
            funFacts = "\n📌Ostriches can run up to 60 mph!🏃‍♂️\n📌They have the largest eyes of any land animal.👀\n📌An ostrich egg is the biggest in the world, weighing about 3 pounds!🥚\n📌Ostriches don’t bury their heads in the sand—it’s a myth!🧐\n📌They have two-toed feet, perfect for running fast.👣"
        )

        val koala = Animal(
            name = "Koala",
            description = "Koalas are tree-dwelling marsupials known for their sleepy lifestyle and love for eucalyptus leaves.",
            imageResIds = listOf(R.drawable.img_62, R.drawable.img_63, R.drawable.img_64),
            habitat = "Eucalyptus forests of Australia",
            diet = "Herbivorous, feeding almost exclusively on eucalyptus leaves",
            funFacts = "\n📌Koalas sleep up to 20 hours a day!😴\n📌They have fingerprints just like humans!🖐️\n📌Koalas rarely drink water since they get moisture from leaves.💧\n📌A baby koala (joey) stays in its mother’s pouch for 6 months.👶\n📌Koalas have a special digestive system to break down toxic eucalyptus leaves.🌿"
        )

        val hyena = Animal(
            name = "Hyena",
            description = "Hyenas are intelligent, social predators known for their strong jaws and distinctive laughing calls.",
            imageResIds = listOf(R.drawable.img_65, R.drawable.img_66, R.drawable.img_67),
            habitat = "Savannas, forests, and grasslands of Africa",
            diet = "Carnivorous, scavenging or hunting for food",
            funFacts = "\n📌Hyenas are more closely related to cats than dogs!🐾\n📌They have one of the strongest bite forces in the animal kingdom.🦷\n📌Hyenas ‘laugh’ to communicate with their clan members.😂\n📌They are excellent hunters, not just scavengers.🐺\n📌Hyena societies are matriarchal, with females leading the clans.👑"
        )


        val rhino = Animal(
            name = "Rhinoceros",
            description = "Rhinos are large, thick-skinned herbivores known for their strong horns and prehistoric appearance.",
            imageResIds = listOf(R.drawable.img_68, R.drawable.img_69, R.drawable.img_70),
            habitat = "Grasslands and forests of Africa and Asia",
            diet = "Herbivorous, feeding on leaves, grass, and shoots",
            funFacts = "\n📌A rhino’s horn is made of keratin, the same material as human nails!🦏\n📌Despite their size, rhinos can run up to 35 mph!🏃\n📌Rhinos have poor eyesight but a strong sense of smell.👃\n📌They love rolling in mud to keep cool and protect their skin!🛁\n📌Baby rhinos stay with their mothers for up to 3 years.👩‍👦"
        )

        val arcticFox = Animal(
            name = "Arctic Fox",
            description = "Arctic foxes are small but resilient animals adapted to survive in freezing temperatures.",
            imageResIds = listOf(R.drawable.img_71, R.drawable.img_72, R.drawable.img_73),
            habitat = "Tundras of the Arctic regions",
            diet = "Omnivorous, eating small mammals, birds, and berries",
            funFacts = "\n📌Arctic foxes change fur color with the seasons—white in winter and brown in summer!❄️☀️\n📌They have furry paws to walk on snow without getting cold.🐾\n📌Their thick tails help keep them warm when curled up.🌀\n📌Arctic foxes can survive in temperatures as low as -50°C!🥶\n📌They have excellent hearing and can locate prey under the snow.👂"
        )


        val jaguar = Animal(
            name = "Jaguar",
            description = "Jaguars are powerful and elusive big cats, known for their stunning spotted coats and strong bite.",
            imageResIds = listOf(R.drawable.img_74, R.drawable.img_75, R.drawable.img_76),
            habitat = "Rainforests and grasslands of South America",
            diet = "Carnivorous, hunting deer, capybaras, and fish",
            funFacts = "\n📌Jaguars have the strongest bite of all big cats!💀\n📌Unlike most cats, they love swimming.🌊\n📌Their spots help them blend into the jungle.🍃\n📌Jaguars can jump up to 10 feet in the air!🦘\n📌They often hunt by ambushing their prey from trees.🌳"
        )

        val otter = Animal(
            name = "Otter",
            description = "Otters are playful, aquatic mammals known for their intelligence and use of tools.",
            imageResIds = listOf(R.drawable.img_77, R.drawable.img_78, R.drawable.img_79),
            habitat = "Rivers, lakes, and coastal waters worldwide",
            diet = "Carnivorous, eating fish, shellfish, and small mammals",
            funFacts = "\n📌Otters hold hands while sleeping to stay together.👐\n📌They use rocks to crack open shellfish!🪨\n📌Otters have the thickest fur of any animal.🧥\n📌They can close their ears and nostrils while swimming.🏊\n📌Some otters build ‘rafts’ by linking together in groups.🚣"
        )

        val bison = Animal(
            name = "Bison",
            description = "Bison are massive, shaggy-haired mammals known for their strength and endurance in harsh climates.",
            imageResIds = listOf(R.drawable.img_80, R.drawable.img_81, R.drawable.img_82),
            habitat = "Grasslands and forests of North America",
            diet = "Herbivorous, grazing on grasses and shrubs",
            funFacts = "\n📌Bison can weigh over 2,000 pounds!💪\n📌They can run up to 35 mph despite their size!🏃\n📌Bison use their heads like snowplows in winter.❄️\n📌They roll in dirt to keep away insects and parasites.🐜\n📌A group of bison is called a ‘herd’.👨‍👩‍👧‍👦"
        )



        val animals = listOf(lion, elephant, giraffe, panda, zebra, tiger, cheetah, kangaroo, polarBear, wolf, flamingo, penguin, redPanda, crocodile, peacock, meerkat, ostrich, koala, hyena, rhino, arcticFox, jaguar, otter, bison)

        val adapter = AnimalAdapter(this, animals)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { position ->
            val intent = Intent(this, AnimalDetailActivity::class.java)
            intent.putExtra("animal", animals[position])
            startActivity(intent)

        }
    }
}