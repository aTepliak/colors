# To use JPA instead of CSV source:
* add annotation @Primary  to PersonsRepositoryJPAImpl
* remove @Primary PersonsRepositoryCSVImpl

Note: the csv is not changed, but before writing to it, the line breaks are added, if not presenet