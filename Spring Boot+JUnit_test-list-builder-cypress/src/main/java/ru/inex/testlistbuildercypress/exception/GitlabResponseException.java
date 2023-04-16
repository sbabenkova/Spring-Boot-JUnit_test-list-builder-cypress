package ru.inex.testlistbuildercypress.exception;

public class GitlabResponseException extends RuntimeException {
    /**
     * Описание общего исключения в случае, если получен некорректный ответ из гитлаба
     * @param message Выводимое сообщение
     */
    public GitlabResponseException(String message) {
        super(message);
    }
}
