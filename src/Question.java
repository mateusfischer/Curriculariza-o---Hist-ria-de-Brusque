public class Question {
    int id;
    int correctOption; // 1, 2 ou 3
    int errorImageId;

    public Question(int id, int correctOption, int errorImageId) {
        this.id = id;
        this.correctOption = correctOption;
        this.errorImageId = errorImageId;
    }
}
