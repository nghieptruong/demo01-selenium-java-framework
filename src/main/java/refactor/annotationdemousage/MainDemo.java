package refactor.annotationdemousage;

import refactor.pages.CustomLoginPage;

public class MainDemo {
    public static void main(String[] args) {
        CustomLoginPage customLoginPage = new CustomLoginPage();
//        ReflectUtils.printAllFields(customLoginPage);
        CustomFindByProcessor.processAnnotations(customLoginPage);
    }
}
