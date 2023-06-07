package com.bangkit.capstonebangkitv1.camera

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.hardware.camera2.CameraManager
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.TextureView
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import com.bangkit.capstonebangkitv1.databinding.ActivityCameraBinding
import com.bangkit.capstonebangkitv1.home.HomePageActivity
import com.bangkit.capstonebangkitv1.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null
    lateinit var model:Model
    lateinit var labels:List<String>
    val paint = Paint()
    lateinit var bitmap: Bitmap
    lateinit var imageView: ImageView
    lateinit var handler: Handler
    lateinit var handlerThread: HandlerThread
    lateinit var textureView: TextureView
    lateinit var cameraManager: CameraManager
    lateinit var imageProcessor: ImageProcessor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model = Model.newInstance(this)
        labels = FileUtil.loadLabels(this, "label.txt")
        imageView = binding.imageView
        textureView = binding.textureView
        imageProcessor = ImageProcessor.Builder().add(ResizeOp(192, 192, ResizeOp.ResizeMethod.BILINEAR)).build()
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        handlerThread = HandlerThread("videoThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)

        paint.setColor(Color.YELLOW)
        machine()
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
        switchCam()
        closeCam()

    }

    private fun switchCam(){
        binding.switchCamera.setOnClickListener {
            cameraSelector = if (cameraSelector.equals(CameraSelector.DEFAULT_BACK_CAMERA)) CameraSelector.DEFAULT_FRONT_CAMERA
            else CameraSelector.DEFAULT_BACK_CAMERA
            startCamera()
        }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun closeCam(){
        binding.closeCamera.setOnClickListener {
            val intent= Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun machine() {
        textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                startCamera()
            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {

            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
                bitmap = textureView.bitmap!!
                var tensorImage = TensorImage(DataType.UINT8)
                tensorImage.load(bitmap)
                tensorImage = imageProcessor.process(tensorImage)

                val inputFeature0 =
                    TensorBuffer.createFixedSize(intArrayOf(1, 1, 1, 3), DataType.UINT8)
                inputFeature0.loadBuffer(tensorImage.buffer)

                // Runs model inference and gets result.
                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray
                val outputFeature1 = outputs.outputFeature1AsTensorBuffer.floatArray
                val outputFeature2 = outputs.outputFeature2AsTensorBuffer.floatArray
                val outputFeature3 = outputs.outputFeature3AsTensorBuffer.floatArray
                val outputFeature4 = outputs.outputFeature4AsTensorBuffer.floatArray
                val outputFeature5 = outputs.outputFeature5AsTensorBuffer.floatArray
                val outputFeature6 = outputs.outputFeature6AsTensorBuffer.floatArray
                val outputFeature7 = outputs.outputFeature7AsTensorBuffer.floatArray

                var mutable = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                var canvas = Canvas(mutable)
                var h = bitmap.height
                var w = bitmap.width
                var x = 0

                Log.d("output__", outputFeature0.size.toString())
                while (x <= 49) {
                    if (outputFeature0.get(x + 2) > 0.45) {
                        canvas.drawCircle(
                            outputFeature0.get(x + 1) * w,
                            outputFeature0.get(x) * h,
                            10f,
                            paint
                        )
                    }
                    x += 3
                    if (outputFeature0.get(x + 2) > 0.45) {
                        canvas.drawCircle(
                            outputFeature1.get(x + 1) * w,
                            outputFeature1.get(x) * h,
                            10f,
                            paint
                        )
                    }
                    x += 3
                    if (outputFeature0.get(x + 2) > 0.45) {
                        canvas.drawCircle(
                            outputFeature2.get(x + 1) * w,
                            outputFeature2.get(x) * h,
                            10f,
                            paint
                        )
                    }
                    x += 3
                    if (outputFeature0.get(x + 2) > 0.45) {
                        canvas.drawCircle(
                            outputFeature3.get(x + 1) * w,
                            outputFeature3.get(x) * h,
                            10f,
                            paint
                        )
                    }
                    x += 3
                    if (outputFeature0.get(x + 2) > 0.45) {
                        canvas.drawCircle(
                            outputFeature4.get(x + 1) * w,
                            outputFeature4.get(x) * h,
                            10f,
                            paint
                        )
                    }
                    x += 3
                    if (outputFeature0.get(x + 2) > 0.45) {
                        canvas.drawCircle(
                            outputFeature5.get(x + 1) * w,
                            outputFeature5.get(x) * h,
                            10f,
                            paint
                        )
                    }
                    x += 3
                    if (outputFeature0.get(x + 2) > 0.45) {
                        canvas.drawCircle(
                            outputFeature6.get(x + 1) * w,
                            outputFeature6.get(x) * h,
                            10f,
                            paint
                        )
                    }
                    x += 3
                    if (outputFeature0.get(x + 2) > 0.45) {
                        canvas.drawCircle(
                            outputFeature7.get(x + 1) * w,
                            outputFeature7.get(x) * h,
                            10f,
                            paint
                        )
                    }
                    x += 3
                }

                imageView.setImageBitmap(mutable)
            }
        }
    }

    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        model.close()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}