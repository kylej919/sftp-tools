# Sftp and CSV parsing Demo

This is a sample usage of the JSch Java library to connect to an SFTP server and operate against it. This particular
example is written in Kotlin.

### Connecting to an SFTP server

to configure connection to an SFTP server, you need to provide the following information in application.yml file:

```yaml
sftp:
  host: sftp://hostname:port
  username: username
  password: password
```

in the SftpConnection class, I added a way to connect to a server using a private key instead of a password, although I don't demo it's usage in a test

then wherever we want to interact with the server, we can instantiate it with the SftpService:

```kotlin
val sftp = sftpService.getConnection()
sftp.listFiles("/path/to/directory")
sftp.downloadFile("/path/to/file")
sftp.uploadFile("/path/to/file")
// ...
sftp.close()
```

### Parsing CSV files

The demo also shows how to parse lines from a CSV file into a list of Java objects using the OpenCSV library.

The first step is to define a data class that represents the structure of the CSV file, and each field should be annotated with the @CsvBindByName annotation to map it to the corresponding column in the CSV file. Ex:

```kotlin
data class PersonCsvRecord(
    @CsvBindByName(column = "First Name")
    val firstName: String,
    @CsvBindByName(column = "Last Name")
    val lastName: String,
    @CsvBindByName(column = "Email")
    val email: String,
    @CsvBindByName(column = "Age")
    val age: Int
)
```

Then, we can use the CSVReader class to read the CSV file and parse its content into a list of Person objects. Say we start with a CSV file contents in a string named 'csvContents', we can do the following:

```kotlin
fun parseCsv(csvContent: String): List<PersonCsvRecord> {
    StringReader(csvContent).use {
        val csvToBean = CsvToBeanBuilder<ProductDataCsvRecord>(it)
            .withType(PersonCsvRecord::class.java)
            .withIgnoreLeadingWhiteSpace(true)
            .build()
        return csvToBean.parse()
    }
}
```
It's possible to do the opposite with the OpenCSV library as well, construct a CSV file from a list of objects, but I didn't demo it here.


That's all!