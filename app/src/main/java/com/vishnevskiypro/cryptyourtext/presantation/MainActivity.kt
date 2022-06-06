package com.vishnevskiypro.cryptyourtext.presantation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vishnevskiypro.cryptyourtext.databinding.ActivityMainBinding
import com.vishnevskiypro.cryptyourtext.domain.models.Key
import com.vishnevskiypro.cryptyourtext.domain.models.Message
import com.vishnevskiypro.cryptyourtext.domain.usecase.CopyMessageUseCase
import com.vishnevskiypro.cryptyourtext.domain.usecase.CryptMessageUseCase
import com.vishnevskiypro.cryptyourtext.domain.usecase.PasteMessageUseCase
import com.vishnevskiypro.cryptyourtext.domain.usecase.ShareMessageUseCase

class MainActivity : AppCompatActivity() {
    val clipboard by lazy { getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    private lateinit var binding: ActivityMainBinding
    private val cryptMessageUseCase = CryptMessageUseCase()
    private val shareMessageUseCase = ShareMessageUseCase()
    private val copyMessageUseCase = CopyMessageUseCase()
    private val pasteMessageUseCase = PasteMessageUseCase()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnCrypt.setOnClickListener {
            val key = Key(binding.keyEdit.text.toString())
            val message = Message(binding.msgEdit.text.toString())
            val result: Message = cryptMessageUseCase.execute(message, key)
            binding.msgEdit.setText(result.messageText)

        }

        binding.btnShare.setOnClickListener {
            val message = Message(binding.msgEdit.text.toString())
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, message.messageText)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(intent, "Share with")
            startActivity(shareIntent)
        }

        binding.btnCopy.setOnClickListener {
            val message = Message(binding.msgEdit.text.toString())
            val clip = ClipData.newPlainText("copy text", message.messageText)
            clipboard.setPrimaryClip(clip)
        }

        binding.btnPaste.setOnClickListener {
            val clipData = clipboard?.primaryClip
            val item = clipData?.getItemAt(0)
            binding.msgEdit.setText(item?.text)

        }
    }
}