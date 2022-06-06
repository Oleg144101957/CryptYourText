package com.vishnevskiypro.cryptyourtext.domain.usecase

import com.vishnevskiypro.cryptyourtext.domain.models.Key
import com.vishnevskiypro.cryptyourtext.domain.models.Message
import kotlin.experimental.xor

class CryptMessageUseCase {

    fun execute (message: Message, key: Key) : Message {
        val charset = Charsets.UTF_8
        val outputBytes = ByteArray(message.messageText.length)
        val inputBytes = message.messageText.toByteArray()
        val keyBytes = key.keyText.toByteArray()

        for (i in inputBytes.indices){
            outputBytes[i] = (inputBytes[i] xor keyBytes[i%key.keyText.length])
        }

        return Message(outputBytes.toString(charset))
    }
}
