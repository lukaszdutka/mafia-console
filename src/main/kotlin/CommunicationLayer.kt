object CommunicationLayer {
    private var small = 15
    private var medium = 30
    private var big = 50
    private var shouldShowDebugMessages: Boolean = true

    fun setup(shouldShowDebugMessages: Boolean) {
        this.shouldShowDebugMessages = shouldShowDebugMessages;
    }

    fun smallMessage(message: String) = messageWithEqualSigns(message, small)
    fun mediumMessage(message: String) = messageWithEqualSigns(message, medium)
    fun bigMessage(message: String) = messageWithEqualSigns(message, big)

    private fun messageWithEqualSigns(message: String, len: Int) {
        println("=".repeat(len))
        println(message)
        println("=".repeat(len))
    }

    fun messageToSay(message: String) {
        println("Mistrz Gry, powiedzieć graczom: $message")
    }

    fun roleAnnounce(role: String) {
        var roleWithSpaces = " $role "
        if (roleWithSpaces.length % 2 != 0) roleWithSpaces = "$roleWithSpaces "
        println("=".repeat(medium))
        println("=".repeat((medium - roleWithSpaces.length) / 2) + roleWithSpaces + "=".repeat((medium - roleWithSpaces.length) / 2))
        println("=".repeat(medium))
        //example:
        //==================================================
        //=================== Terrorysta ===================
        //==================================================
    }

    fun debug(message: String) {
        if (shouldShowDebugMessages) {
            println("DEBUG: $message")
        }
    }
}