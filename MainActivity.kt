package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val maVue = findViewById<ImageView>(R.id.imageDedicace)
        val grandTitre = findViewById<TextView>(R.id.textDedicace)

        // Champ de saisie des infos (nom, âge)
        val nomUtilisateur = findViewById<EditText>(R.id.editName)
        val ageUtilisateur = findViewById<EditText>(R.id.editAge)

        val radioBtn = findViewById<RadioGroup>(R.id.radioBtn)

        val checkBox1 = findViewById<CheckBox>(R.id.premierCheckBox)
        val checkBox2 = findViewById<CheckBox>(R.id.secondCheckBox)
        val checkBox3 = findViewById<CheckBox>(R.id.thirdCheckBox)

        val boutonEnvoyer = findViewById<Button>(R.id.btnEnvoyer)

        // Fonction pour vérifier l'état des champs | Permet d'activer ou de griser le bouton d'envoi
        fun verifierChamps() {
            val nomOk = nomUtilisateur.text.isNotEmpty()
            val ageOk = ageUtilisateur.text.isNotEmpty()

            val radioBtnOk = radioBtn.checkedRadioButtonId != -1

            val checkBox1Ok = checkBox1.isChecked()
            val checkBox2Ok = checkBox2.isChecked()
            val checkBox3Ok = checkBox3.isChecked()

            boutonEnvoyer.isEnabled = nomOk && ageOk && radioBtnOk && (checkBox1Ok || checkBox2Ok || checkBox3Ok)
        }

        // Changer le texte 'DEDICACE' sur l'image par le texte saisi par l'utilisateur
        nomUtilisateur.addTextChangedListener {
            val nouveauTitre = it.toString()

            if(nouveauTitre.isEmpty()){
                grandTitre.text = "DEDICACE"
            } else {
                grandTitre.text = nouveauTitre
            }

            verifierChamps()
        }

        // Appliquer la fonction à chaque élément du formulaire
        ageUtilisateur.doOnTextChanged {_,_,_,_ -> verifierChamps()}
        radioBtn.setOnCheckedChangeListener { _, _ -> verifierChamps() }

        checkBox2.setOnCheckedChangeListener {_,_ -> verifierChamps()}
        checkBox3.setOnCheckedChangeListener {_,_ -> verifierChamps()}

        // Action sur le premier CheckBox
        checkBox1.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                Toast.makeText(this, "Excellent choix, le Basket est un super sport !", Toast.LENGTH_LONG).show()
            }
            verifierChamps()
        }

        // Passage à l'écran 2 après la soumission du formulaire
        boutonEnvoyer.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)

            // Vider l'écran pour donner l'impression d'un nouveau formulaire (encore basique)
            nomUtilisateur.text.clear()
            ageUtilisateur.text.clear()
            radioBtn.clearCheck()
            checkBox1.isChecked = false
            checkBox2.isChecked = false
            checkBox3.isChecked = false

            startActivity(intent)
        }
    }
}