package com.github.zzwtsy.tools

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.jvm.Throws

object Md5 {
    /**
     * 计算给定字节数组的 MD5 散列值，并返回结果作为十六进制字符串。
     *
     * @param bytes 需要计算散列值的字节数组。
     * @return 字节数组的 MD5 散列值，作为十六进制字符串。
     */
    @Throws(NoSuchAlgorithmException::class)
    fun calculateImageMD5(bytes: ByteArray): String {
        // 创建 MessageDigest 实例并计算散列值
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(bytes)

        // 将计算得到的散列值转换为十六进制字符串并返回
        return digest.joinToString("") { "%02x".format(it) }
    }
}