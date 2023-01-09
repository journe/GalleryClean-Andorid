package tech.jour.galleryclean

import android.app.Application
import android.os.Build
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.VideoFrameDecoder
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class AppApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this).crossfade(true).components {
            add(VideoFrameDecoder.Factory())
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()
    }
}
