package trainingxyz.support.api;

public enum Endpoint {
    CREATE("/api_testing/product/create.php"),
    READ("/api_testing/product/read.php"),
    READ_ONE("/api_testing/product/read_one.php"),
    UPDATE("/api_testing/product/update.php"),
    READ_CATEGORY("/api_testing/category/read.php"),
    DELETE("/api_testing/product/delete.php");

    public final String path;

    Endpoint(String p){
        this.path = p;
    }
}
