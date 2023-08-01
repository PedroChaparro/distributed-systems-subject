package application;

import domain.WebToPdfConverter;

import java.io.File;

public class ConverterUseCases {
    private WebToPdfConverter converterInstance;

    public ConverterUseCases(WebToPdfConverter converterInstance) {
        this.converterInstance = converterInstance;
    }

    public String Convert(String url, String name) {
        return this.converterInstance.convert(url, name);
    }
}
