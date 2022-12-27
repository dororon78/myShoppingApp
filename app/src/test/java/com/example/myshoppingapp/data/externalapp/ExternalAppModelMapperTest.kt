package com.example.myshoppingapp.data.externalapp

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class ExternalAppModelMapperTest {

    private lateinit var externalAppModelMapper: ExternalAppModelMapper

    private val mockExternalAppInfoDto: ExternalAppInfoDto = mockk()

    @Before
    fun setUp() {
        every { mockExternalAppInfoDto.name } returns DTO_NAME
        every { mockExternalAppInfoDto.packageName } returns DTO_PACKAGE_NAME
        every { mockExternalAppInfoDto.installable } returns DTO_INSTALLABLE
        every { mockExternalAppInfoDto.executable } returns DTO_EXECUTABLE
        every { mockExternalAppInfoDto.marketUri } returns DTO_MARKET_URI
        externalAppModelMapper = ExternalAppModelMapper()
    }

    @Test
    fun toDomainLayer_MappingToDomainModel_ReturnsWithSameValue() {
        val result = externalAppModelMapper.toDomainLayer(mockExternalAppInfoDto)

        assertEquals(
            DTO_NAME,
            result.name
        )

        assertEquals(
            DTO_PACKAGE_NAME,
            result.packageName
        )

        assertEquals(
            DTO_INSTALLABLE,
            result.installable
        )
        assertEquals(
            DTO_EXECUTABLE,
            result.executable
        )
    }

    companion object {
        const val DTO_NAME = "DTO_NAME"
        const val DTO_PACKAGE_NAME = "DTO_PACKAGE_NAME"
        const val DTO_INSTALLABLE = false
        const val DTO_EXECUTABLE = false
        const val DTO_MARKET_URI = "market://details?id=DTO_PACKAGE_NAME"
    }
}