package com.example.aircraftinquiry.AllBean

data class Ticketbean(
    val code: Int,
    val msg: String,
    val rows: List<Row>,
    val total: Int
)