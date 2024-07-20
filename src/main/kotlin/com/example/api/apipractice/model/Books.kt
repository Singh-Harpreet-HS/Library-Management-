package com.example.api.apipractice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "books")
data class Books(
    @Id val id: String? = null,
    var title: String?,
    var author: String?,
    var description: String?,
    var price: BigDecimal,
    var genre: String?,
    var publishedDate: String?
)

/*{
    "title":"The Diary of a young girl",
    "author":"Anne Frank",
    "description":"It is about a holocaust victim",
    "price":59,
    "genre":"True Story"
}
*/
