package characters

import characters.implementations.*

object CharacterFactory {
    val allCharacters = listOf(
        //miasto
        KomisarzCattani(),
        Lekarz(),
        Sedzia(),
        PLO(),
        Swiety(),
        SzybkiZMiasta(),

        //syndykat
        SzefSyndykatu(),
        Kolejarz(),

        //mafia
        SzefMafii(),
        Kokietka(),
        SzybkiZMafii(),
        //Terrorysta() //turned off due to todos
    );


}