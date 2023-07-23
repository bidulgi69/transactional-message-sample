package enumeration

enum class PaymentAgency {

    TOSS,
    KAKAO_BANK,
    NAVER_PAY;

    companion object {
        fun of(agency: String) =
            when (agency) {
                "toss" -> TOSS
                "kakao_bank" -> KAKAO_BANK
                else -> NAVER_PAY
            }
    }
}