package com.rafarocket.coderiochallenge.ui.loadimage

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.rafarocket.coderiochallenge.R
import com.rafarocket.coderiochallenge.Utils
import com.rafarocket.coderiochallenge.databinding.FragmentLoadImageBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class LoadImageFragment : Fragment() {

    private lateinit var binding: FragmentLoadImageBinding
    private lateinit var imageCamera: ImageView
    private var imageUri: Uri? = null
    private lateinit var buttonTakePic: Button
    private lateinit var buttonLoadImage: Button
    private lateinit var buttonUploadImage: Button
    private lateinit var fireStore: FirebaseFirestore
    private lateinit var titleMessage: TextView

    private val photoActivityResult = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            imageCamera.setImageURI(imageUri)
            titleMessage.text = getString(R.string.message_takenorselected_picture)
        } else {
            imageUri = null
        }
    }

    private var imagePickerActivityResult: ActivityResultLauncher<Intent> = activityResultLauncher()

    private var permissionActivityResult: ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            buttonTakePic.performClick()
        } else {
            Toast.makeText(requireContext(), getString(R.string.message_must_grant_permissions), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoadImageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Database reference firebase
        fireStore = Firebase.firestore

        imageCamera = binding.imageviewCamera
        buttonTakePic = binding.takePic
        buttonLoadImage = binding.selectPic
        buttonUploadImage = binding.uploadPic
        titleMessage = binding.titleUpload

        buttonTakePic.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionActivityResult.apply {
                    launch(Manifest.permission.CAMERA)
                    launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            } else {
                imageUri = createImageUri()
                photoActivityResult.launch(imageUri)
            }

        }

        buttonLoadImage.setOnClickListener {
            selectImage()
        }

        buttonUploadImage.setOnClickListener {
            uploadImage()
        }

        return root
    }

    private fun createImageUri(): Uri {
        val image = File(requireContext().filesDir, "customImagesCoderioApp.png")
        return FileProvider.getUriForFile(
            requireContext(),
            "com.rafarocket.coderiochallenge.fileProvider",
            image
        )
    }

    private fun selectImage() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            imagePickerActivityResult.launch(galleryIntent)
        } else {
            requireActivity().startActivity(galleryIntent)
        }

    }

    private fun getFileName(context: Context, uri: Uri): String? {
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if(cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME) ?: 0)
                    }
                }
            }
        }
        return uri.path?.lastIndexOf('/')?.let { uri.path?.substring(it) }
    }

    private fun activityResultLauncher() =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null && result.resultCode == RESULT_OK) {
                imageUri = result.data?.data as Uri
                imageCamera.setImageURI(imageUri)
                titleMessage.text = getString(R.string.message_takenorselected_picture)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.message_check_permissions),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            imageUri = data?.data as Uri
            imageCamera.setImageURI(imageUri)
            titleMessage.text = getString(R.string.message_takenorselected_picture)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.message_check_permissions),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun uploadImage() {

        if (imageUri != null) {
            val fileName = getFileName(requireContext(), imageUri as Uri) as String

            val imageToUpload = mutableMapOf<String, Uri>().apply {
                put(fileName, imageUri as Uri)
            }

            fireStore.collection("/Pictures").add(imageToUpload).addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "Successfully added picture $fileName",
                    Toast.LENGTH_SHORT
                ).show()

                titleMessage.text = getString(R.string.message_picture_loaded)

            }.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Something went wrong with $fileName",
                    Toast.LENGTH_SHORT
                ).show()
                it.printStackTrace()
            }.addOnCanceledListener {
                Toast.makeText(
                    requireContext(),
                    "Something went wrong with $fileName",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(requireContext(), "No image loaded yet", Toast.LENGTH_SHORT).show()
        }

        if (!Utils().checkForInternet(requireContext())) {
            val message = SpannableString(getString(R.string.message_internet_warning))
            Utils().createAlertDialog(requireContext(), getString(R.string.message_no_internet), message)
        }
    }

}