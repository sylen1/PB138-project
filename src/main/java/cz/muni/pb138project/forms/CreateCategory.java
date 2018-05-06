package cz.muni.pb138project.forms;

import javax.validation.constraints.NotBlank;

public class CreateCategory {
    @NotBlank(message = "Must not be blank")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CreateCategory{" +
                "name='" + name + '\'' +
                '}';
    }
}
