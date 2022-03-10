package com.example.githubusersearch.framework.datasource.network.mappers

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import com.example.githubusersearch.business.domain.model.ReadMe
import com.example.githubusersearch.business.util.EntityMapper
import com.example.githubusersearch.framework.datasource.network.model.ReadMeDto
import java.nio.charset.StandardCharsets

@RequiresApi(Build.VERSION_CODES.O)
class ReadMeMapper: EntityMapper<ReadMeDto, ReadMe> {

    override fun mapFromEntity(entity: ReadMeDto): ReadMe {
        val decodedContent = Base64.decode(entity.content, Base64.NO_WRAP)
        val decodedContentString = String(decodedContent, StandardCharsets.UTF_8)
        return ReadMe(content = decodedContentString)
    }

    override fun mapFromDomainModel(domainModel: ReadMe): ReadMeDto {
        val encodedContent = domainModel.content.toByteArray(StandardCharsets.UTF_8)
        val encodedContentString = Base64.encodeToString(encodedContent, Base64.NO_WRAP)
        return ReadMeDto(content = encodedContentString)
    }
}