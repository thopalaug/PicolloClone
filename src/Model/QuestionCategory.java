package Model;

public class QuestionCategory {

    String categoryName;
    String shortDescription;

    public QuestionCategory(String categoryName, String shortDescription){
        this.categoryName = categoryName;
        this.shortDescription = shortDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
