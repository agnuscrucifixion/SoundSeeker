package ru.padwicki.soundseeker.pyBase;
import org.python.util.PythonInterpreter;
public class pyYandex {

    public static void yandexStart() {
        try (PythonInterpreter interp = new PythonInterpreter()) {

            interp.execfile("./src/main/resources/pyLib/main.py");
        }
    }
}
