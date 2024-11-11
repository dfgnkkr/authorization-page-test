package ru.dfgnkkr.forprotei.autotests.data;

public class SurveyData {
    private int testIndex;
    private String email;
    private String name;
    private String gender;
    private boolean checkOption11;
    private boolean checkOption12;
    private int radioOption;
    private String expectedOutput;

    public int getTestIndex() { return testIndex; }
    public void setTestIndex(int testIndex) { this.testIndex = testIndex; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public boolean isCheckOption11() { return checkOption11; }
    public void setCheckOption11(boolean checkOption11) { this.checkOption11 = checkOption11; }

    public boolean isCheckOption12() { return checkOption12; }
    public void setCheckOption12(boolean checkOption12) { this.checkOption12 = checkOption12; }

    public int getRadioOption() { return radioOption; }
    public void setRadioOption(int radioOption) { this.radioOption = radioOption; }

    public String getExpectedOutput() { return expectedOutput; }
    public void setExpectedOutput(String expectedOutput) { this.expectedOutput = expectedOutput; }
}
