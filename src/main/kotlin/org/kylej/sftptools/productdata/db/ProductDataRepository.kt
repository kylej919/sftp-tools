package org.kylej.sftptools.productdata.db

import org.kylej.sftptools.productdata.model.ProductData
import org.springframework.data.jpa.repository.JpaRepository

interface ProductDataRepository : JpaRepository<ProductData, String>