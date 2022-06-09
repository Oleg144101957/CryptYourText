package com.vishnevskiypro.cryptyourtext.domain.usecase

import com.vishnevskiypro.cryptyourtext.domain.models.Key
import com.vishnevskiypro.cryptyourtext.domain.models.Message
import kotlin.experimental.xor

class CryptMessageUseCase {

    fun encrypt (messageObject: Message, key: Key): Message {
        val originalBytes = messageObject.messageText.toByteArray()
        val encryptedByteArray = originalBytes.copyOf()
        val keyBytes = key.keyText.toByteArray()

        for (i in originalBytes.indices){
            encryptedByteArray[i] = originalBytes[i] xor keyBytes[i%keyBytes.size]
        }

        return Message(encryptedByteArray.joinToString())
    }

    fun decrypt (messageObject: Message, key: Key): Message{
        val cryptedListOfStrings = messageObject.messageText.split(", ")
        val decryptedByteArray = ByteArray(cryptedListOfStrings.size)
        var keyBytes = key.keyText.toByteArray()

        for (i in cryptedListOfStrings.indices){
            decryptedByteArray[i] = cryptedListOfStrings[i].toByte() xor keyBytes[i%keyBytes.size]
        }

        return Message(String(decryptedByteArray))
    }

}
