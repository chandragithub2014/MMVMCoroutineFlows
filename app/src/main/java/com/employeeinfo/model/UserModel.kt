package com.employeeinfo.model

data class UserModel(
    val `data`: List<Data>,
    val page: Int, // 1
    val per_page: Int, // 6
    val support: Support,
    val total: Int, // 12
    val total_pages: Int // 2
) {
    data class Data(
        val avatar: String, // https://reqres.in/img/faces/1-image.jpg
        val email: String, // george.bluth@reqres.in
        val first_name: String, // George
        val id: Int, // 1
        val last_name: String // Bluth
    )

    data class Support(
        val text: String, // To keep ReqRes free, contributions towards server costs are appreciated!
        val url: String // https://reqres.in/#support-heading
    )
}