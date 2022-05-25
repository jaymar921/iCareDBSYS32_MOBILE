package me.jaymar.icaredbsys32_mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import me.jaymar.icaredbsys32_mobile.Database.Database
import me.jaymar.icaredbsys32_mobile.data.InquiryData
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ServiceRequest(private val accountId: String, private val controller: AppCompatActivity) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_service_request, container, false)

        var serviceCode = "-"

        // textViews
        val serviceType: TextView = view.findViewById(R.id.serviceCodeText)
        val servicePrice: TextView = view.findViewById(R.id.servicePriceText)
        val vaccineType: Spinner = view.findViewById(R.id.vaccine_type_dropdown)
        val scheduleDate: TextView = view.findViewById(R.id.schedDateText)
        val pets: Spinner = view.findViewById(R.id.pet_dropdown)
        val venue: Spinner = view.findViewById(R.id.venue_dropdown)

        // disable some fields
        vaccineType.isEnabled = false
        serviceType.isEnabled = false
        servicePrice.isEnabled = false
        var args = arguments?.getString("service_code").toString()

        val vaxTypes = listOf("-","Modified-live (attenuated)","Inactivated (killed)","Recombinant","Toxoid")
        val vaxAdapter = ArrayAdapter(view.context,R.layout.support_simple_spinner_dropdown_item,vaxTypes)
        vaxAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        vaccineType.adapter = vaxAdapter
        when(args){
            "VX" -> {
                serviceCode = "Pet Vaccination"
                vaccineType.isEnabled = true
            }
            "DM" -> serviceCode = "Pet Deworming"
            "TL" -> serviceCode = "Pet Tubal Ligation"
            "AF" -> serviceCode = "Pet Anti-Flea"
            "VA" ->  serviceCode = "Pet Veterinary Assistance"
        }

        serviceType.text = serviceCode
        servicePrice.text = "P ${Database.getServicePrice(args)}";

        // get user pets
        val userPets = Database.getPetInformation(accountId)

        // display the pets into the spinner (Dropdown)
        val petMap = mutableMapOf<String,String>()
        for (x in userPets){
            petMap[x.id] = x.name
        }

        // add convert the map to the array adapter
        val petsArray = mutableListOf<String>()
        for(x in petMap){
            petsArray.add(x.value)
        }
        var adapter = ArrayAdapter(view.context, R.layout.support_simple_spinner_dropdown_item,petsArray)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        pets.adapter = adapter


        // set the date
        var calendar : Calendar = Calendar.getInstance()
        var df = SimpleDateFormat("yyyy-MM-dd")
        scheduleDate.text = df.format(calendar.time)

        // set the venues
        val venues = listOf("Talisay City Hall","Cebu City Hall","Naga City Hall")
        adapter = ArrayAdapter(view.context, R.layout.support_simple_spinner_dropdown_item,venues)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        venue.adapter = adapter



        // buttons
        val submit: Button = view.findViewById(R.id.service_submit_button)
        val cancel: Button = view.findViewById(R.id.service_cancel_button)




        val serviceTitle: TextView = view.findViewById(R.id.service_title_field)
        serviceTitle.text = serviceCode


        // click listeners
        cancel.setOnClickListener{
            val transaction = controller.supportFragmentManager.beginTransaction()
            val services = Services()
            services.accountId = accountId
            transaction.replace(R.id.ui_fragment,services)
            transaction.commit()
            serviceFragment()
        }

        submit.setOnClickListener{
            val serviceCodeValue = args
            var remarks:String = vaccineType.selectedItem.toString()
            if(!vaccineType.isEnabled || remarks=="-")
                remarks = ""
            val scheduleDateValue = scheduleDate.text.toString()
            var petId = ""
            if(petMap.entries.find { it.value == pets.selectedItem }?.key != null)
                petId = petMap.entries.find { it.value == pets.selectedItem }?.key!!
            val venueVal = venue.selectedItem.toString()

            val inquiry = InquiryData(
                petId,
                serviceCodeValue,
                scheduleDateValue,
                venueVal,
                remarks
            )

            Toast.makeText(view.context,"Submitting Request",Toast.LENGTH_SHORT).show()
            if(Database.submitInquiry(inquiry)){
                Toast.makeText(view.context,"Request Sent",Toast.LENGTH_LONG).show()
                serviceFragment()
            }else{
                Toast.makeText(view.context,"Failed to send request...",Toast.LENGTH_LONG).show()
            }

        }

        return view
    }

    private fun serviceFragment(){
        val transaction = controller.supportFragmentManager.beginTransaction()
        val services = Services()
        services.accountId = accountId
        transaction.replace(R.id.ui_fragment,services)
        transaction.commit()
    }

}