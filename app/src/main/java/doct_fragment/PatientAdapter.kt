package doct_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.cscorner.diabetique.Doctor
import com.cscorner.diabetique.Patient
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.cscorner.diabetique.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener


class PatientAdapter(
    options: FirebaseRecyclerOptions<Patient>,
    private val listener: OnItemClickListener,
    private var allPatients: MutableList<Patient> = mutableListOf()
) : FirebaseRecyclerAdapter<Patient, PatientAdapter.PatientViewHolder>(options) {


    private fun submitList(data: List<Patient>) {
        allPatients = data.toMutableList()
        notifyDataSetChanged()
    }



    // Reste du code de l'adaptateur...
    private  val databaseReference = FirebaseDatabase.getInstance().reference.child("patients")


        interface OnItemClickListener {
            fun onItemClick(patient: Patient)
            fun onContactClick(patient: Patient)
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_patient, parent, false)
        return PatientViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int, model: Patient) {

        holder.bind(model)
        holder.itemView.findViewById<Button>(R.id.message_button).setOnClickListener {
            it.tag = model.id
            listener.onContactClick(model)
        }
    }

    inner class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fullNameTextView: TextView = itemView.findViewById(R.id.full_name_text_view)
        private val ageTextView: TextView = itemView.findViewById(R.id.age_text_view)
        private val diabetesTypeTextView: TextView =
            itemView.findViewById(R.id.diabetes_type_text_view)


        fun bind(patient: Patient) {
            fullNameTextView.text = patient.fullName
            ageTextView.text = "Age: ${patient.age}"
            diabetesTypeTextView.text = "Type de diabète: ${patient.diabete}"

            itemView.setOnClickListener {
                listener.onItemClick(patient)
            }
            itemView.setOnLongClickListener {
                listener.onItemClick(patient)
                true
            }

        }
    }






}






