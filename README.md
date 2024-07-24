# Similarity Checker

## Overview

This repository contains my implementation of the `Similarity` class. The primary goal of this project was to use the Map interface to solve the problem of comparing two documents and determining their similarity using the cosine similarity algorithm.

## Description

The `Similarity` class calculates the similarity between two documents based on the cosine similarity of their word frequency vectors. This project involves parsing words from text files, calculating word frequencies, and using these frequencies to determine the cosine similarity between the documents.

## Features

- **Constructors**:
  - `Similarity(String string)`: Initializes the similarity object with a single string.
  - `Similarity(File file)`: Initializes the similarity object with a file.
  
- **Methods**:
  - `int numOfLines()`: Returns the number of lines in the document (constant time).
  - `int numOfWords()`: Returns the number of words in the document (constant time).
  - `int numOfWordsNoDups()`: Returns the number of unique words in the document (constant time).
  - `double euclideanNorm()`: Calculates the Euclidean norm of the word frequency vector.
  - `double dotProduct(Map<String, Integer> map)`: Calculates the dot product of the word frequency vector with another vector.
  - `double distance(Map<String, Integer> map)`: Calculates the cosine distance between the word frequency vector and another vector.
  - `Map<String, Integer> getMap()`: Returns the map of word frequencies.

## Implementation Details

To deepen my understanding of data structures and algorithms, I implemented this project with specific constraints and rules:

- **Cosine Similarity**: Utilizes the cosine similarity algorithm to determine the similarity between two documents based on their word frequency vectors.
- **Map Interface**: Uses the Map interface from the Java Collections Framework to store word frequencies efficiently.
- **String and File Handling**: Handles both string and file inputs to create word frequency vectors.
- **Efficient Operations**:
  - **Constant Time Methods**: Ensures methods like `numOfLines()`, `numOfWords()`, and `numOfWordsNoDups()` run in constant time.
  - **Defensive Coding**: Implements checks to handle large files and ensure the code is robust against various edge cases.

## Usage

To use the `Similarity` class, create an instance and call its methods, or run the provided driver program in the repository.

## Learnings

- **Cosine Similarity**: I gained a deeper understanding of the cosine similarity algorithm and its application in text comparison.
- **Map Interface**: I learned to use the Map interface to store and manipulate word frequencies efficiently.
- **Text Processing**: I developed skills in processing text from strings and files to create frequency vectors.
- **Problem-Solving**: I addressed edge cases and ensured the robustness of the implementation through extensive testing.

## Conclusion

This project was a valuable experience in understanding the intricacies of using the Map interface and the cosine similarity algorithm to compare documents. The `Similarity` class provides an efficient solution for determining the similarity between two documents based on their word frequency vectors.
