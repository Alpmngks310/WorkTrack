package com.worktrack.app.api
import com.worktrack.app.response.AbsensiResponse
import com.worktrack.app.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.worktrack.app.response.StatusAbsensiResponse
import com.worktrack.app.response.RiwayatAbsensiResponse
import com.worktrack.app.response.CutiResponse
import com.worktrack.app.response.RiwayatCutiResponse
import com.worktrack.app.response.StatusPengajuanResponse
import com.worktrack.app.response.RiwayatReimbursementResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.worktrack.app.response.ReimbursementResponse
import retrofit2.http.Multipart
import retrofit2.http.Part
import com.worktrack.app.response.ApprovalCutiResponse
import com.worktrack.app.response.SimpleResponse
import com.worktrack.app.response.MonitoringAbsensiResponse
import com.worktrack.app.response.ApprovalReimbursementResponse
import com.worktrack.app.response.MonitoringCutiResponse
import com.worktrack.app.response.MonitoringReimbursementResponse
interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("checkin.php")
    fun checkIn(
        @Field("id_user") idUser: String
    ): Call<AbsensiResponse>

    @FormUrlEncoded
    @POST("checkout.php")
    fun checkOut(
        @Field("id_user") idUser: String
    ): Call<AbsensiResponse>

    @FormUrlEncoded
    @POST("status_absensi.php")
    fun getStatusAbsensi(
        @Field("id_user") idUser: String
    ): Call<StatusAbsensiResponse>

    @FormUrlEncoded
    @POST("riwayat_absensi.php")
    fun getRiwayatAbsensi(
        @Field("id_user") idUser: String
    ): Call<RiwayatAbsensiResponse>

    @FormUrlEncoded
    @POST("ajukan_cuti.php")
    fun ajukanCuti(
        @Field("id_user") idUser: String,
        @Field("tanggal_mulai") tanggalMulai: String,
        @Field("tanggal_selesai") tanggalSelesai: String,
        @Field("alasan") alasan: String
    ): Call<CutiResponse>

    @FormUrlEncoded
    @POST("riwayat_cuti.php")
    fun getRiwayatCuti(
        @Field("id_user") idUser: String
    ): Call<RiwayatCutiResponse>

    @FormUrlEncoded
    @POST("status_pengajuan.php")
    fun getStatusPengajuan(
        @Field("id_user") idUser: String
    ): Call<StatusPengajuanResponse>

    @FormUrlEncoded
    @POST("riwayat_reimbursement.php")
    fun getRiwayatReimbursement(
        @Field("id_user") idUser: String
    ): Call<RiwayatReimbursementResponse>


    @Multipart
    @POST("ajukan_reimbursement.php")
    fun ajukanReimbursement(

        @Part("id_user") idUser: RequestBody,

        @Part("tanggal") tanggal: RequestBody,

        @Part("nominal") nominal: RequestBody,

        @Part("keterangan") keterangan: RequestBody,

        @Part bukti: MultipartBody.Part

    ): Call<ReimbursementResponse>

    @FormUrlEncoded
    @POST("paid_reimbursement.php")
    fun paidReimbursement(
        @Field("id_reimbursement")
        idReimbursement: String
    ): Call<SimpleResponse>

    @POST("riwayat_pembayaran.php")
    fun getRiwayatPembayaran():
            Call<ApprovalReimbursementResponse>

    @POST("get_monitoring_cuti.php")
    fun getMonitoringCuti():
            Call<MonitoringCutiResponse>
    @FormUrlEncoded
    @POST("approve_cuti.php")
    fun approveCuti(
        @Field("id_cuti") idCuti: String
    ): Call<SimpleResponse>

    @FormUrlEncoded
    @POST("reject_cuti.php")
    fun rejectCuti(
        @Field("id_cuti") idCuti: String
    ): Call<SimpleResponse>

    @POST("get_monitoring_absensi.php")
    fun getMonitoringAbsensi():
            Call<MonitoringAbsensiResponse>
    @POST("get_pengajuan_cuti.php")
    fun getPengajuanCuti(): Call<ApprovalCutiResponse>

    @POST("riwayat_approval_cuti.php")
    fun getRiwayatApprovalCuti():
            Call<ApprovalCutiResponse>

    @POST("get_pengajuan_reimbursement.php")
    fun getPengajuanReimbursement():
            Call<ApprovalReimbursementResponse>

    @POST("get_approved_reimbursement.php")
    fun getApprovedReimbursement():
            Call<ApprovalReimbursementResponse>

    @POST("riwayat_approval_reimbursement.php")
    fun getRiwayatApprovalReimbursement():
            Call<ApprovalReimbursementResponse>

    @FormUrlEncoded
    @POST("approve_reimbursement.php")
    fun approveReimbursement(
        @Field("id_reimbursement")
        idReimbursement: String
    ): Call<SimpleResponse>

    @FormUrlEncoded
    @POST("reject_reimbursement.php")
    fun rejectReimbursement(
        @Field("id_reimbursement")
        idReimbursement: String
    ): Call<SimpleResponse>

    @POST("get_monitoring_reimbursement.php")
    fun getMonitoringReimbursement():
            Call<MonitoringReimbursementResponse>
}