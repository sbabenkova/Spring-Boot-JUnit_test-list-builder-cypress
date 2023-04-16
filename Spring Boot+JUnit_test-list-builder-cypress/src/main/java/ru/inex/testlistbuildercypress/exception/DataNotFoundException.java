package ru.inex.testlistbuildercypress.exception;

public class DataNotFoundException extends RuntimeException {
    /**
     * Описание исключения в случае, если в полученном ответе из гитлаба нет данных
     * @param message Выводимое сообщение
     */
    public DataNotFoundException(String message) {
        super(message);
    }
}
