package tech.jour.galleryclean

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.permissionx.guolindev.PermissionX

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val requestList = ArrayList<String>()
        requestList.add(Manifest.permission.ACCESS_MEDIA_LOCATION)
        requestList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        requestList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        PermissionX.init(this)
            .permissions(requestList)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "需要您同意以下权限才能正常运行", "同意", "拒绝")
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "您没有授予应用存储权限，是否前往设置？", "前往设置", "取消")
            }
            .request { allGranted, _, _ ->
                if (allGranted) {
                    permissionOk()
                } else {
                }
            }

    }

    private fun permissionOk() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}