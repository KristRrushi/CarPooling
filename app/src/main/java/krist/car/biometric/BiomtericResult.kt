package krist.car.biometric

interface BiomtericResult {
    fun onSuccess()
    fun onHardwareError(error: String)
    fun onBiomtericNoneEnrolled()
}