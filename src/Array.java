public class Array {
    // liner search algorithm
    public static int linerSearch(int[] arr, int target) {
        for (int index = 0; index < arr.length; index++)
            if (arr[index] == target) return index;
        return -1;
    }

    public static int linerSearch(double[] arr, double target) {
        for (int index = 0; index < arr.length; index++)
            if (arr[index] == target) return index;
        return -1;
    }
    public static int linerSearch(String[] arr, String target) {
        for (int index = 0; index < arr.length; index++)
            if (arr[index] == target) return index;
        return -1;
    }
}
