package ru.hse.elarateam.web.services;

import org.springframework.beans.factory.annotation.Value;

public interface TokenGenerator {
    String generate();
}
