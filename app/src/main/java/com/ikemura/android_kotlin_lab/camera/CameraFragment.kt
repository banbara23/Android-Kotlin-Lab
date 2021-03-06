package com.ikemura.android_kotlin_lab.camera

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.common.util.concurrent.ListenableFuture
import com.ikemura.android_kotlin_lab.databinding.FragmentCameraBinding
import java.util.concurrent.Executors

class CameraFragment : Fragment() {
    private lateinit var binding: FragmentCameraBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCamera()
    }

    private fun setupCamera() {
        val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder()
            .build()

        // val cameraSelector: CameraSelector = CameraSelector.Builder()
        //     .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        //     .build()

        val imageAnalysis = ImageAnalysis.Builder()
            // .setTargetResolution(Size(1280, 720)) // デフォルトは 640x480になってるので指定不要 https://developer.android.com/training/camerax/configuration?hl=ja
            // .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST) // デフォルトは STRATEGY_KEEP_ONLY_LATEST なので不要
            .build()

        val executor = Executors.newSingleThreadExecutor()

        imageAnalysis.setAnalyzer(executor, QrCodeAnalyzer { result ->
            Log.d("CameraFragment", result.text)
            showDialog(result.text)

            requireActivity().runOnUiThread {
                cameraProvider.unbindAll()
            }
        })

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(viewLifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, imageAnalysis, preview)
        preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
    }

    private fun showDialog(text: String) {
        requireActivity().runOnUiThread {
            AlertDialog.Builder(requireContext()).setMessage(text).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CameraFragment()
    }
}
