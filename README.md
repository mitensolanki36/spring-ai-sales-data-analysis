# spring-ai-sales-data-analysis

# Spring AI Sales Analysis Chatbot
This project is a Spring Boot application that allows you to upload an Excel file containing sales data and then chat with an AI assistant to analyze the data. It uses OpenAI's GPT models for the chat functionality and Postgres Vector as a local vector store.

![image](https://github.com/user-attachments/assets/3028a200-456b-44ee-8690-875efe4428cf)

Features
Upload Excel sales data.

Chat with an AI to get insights, summaries, and recommendations based on your data.

Uses OpenAI's powerful language models.

Local and private data analysis using a local ChromaDB instance.

Easy setup with Docker Compose.

Prerequisites
Java 17 or later

Maven

Docker and Docker Compose

An OpenAI API Key

Getting Started
Clone the repository:

git clone <repository-url>
cd spring-ai-sales-analysis

mvn clean install

Run the services using Docker Compose:

docker-compose up --build

This will start the Spring Boot application on port 8080 and the ChromaDB service on port 8000.

How to Use
Upload your data:
Use a tool like curl or Postman to upload your Excel sales data file (.xlsx).

curl -X POST -F "file=@/path/to/your/sales_data.xlsx" http://localhost:8080/api/upload

You should get a success message once the file is processed and ingested into the vector store.

# Chat with the AI:
Now you can send questions to the chat endpoint.

curl -X POST -H "Content-Type: application/json" -d '{"query": "What were the total sales last month?"}' http://localhost:8080/api/chat

The API will return a JSON response with the AI's answer.

# Example Questions to Ask Your Data
Once you've uploaded your sales data, here are some examples of questions you can ask the chatbot. These are designed to showcase the analytical and recommendation capabilities of the system.

# Basic Data Retrieval
"What was the total revenue for the last quarter?"

"Which product had the highest sales in January?"

"List the top 5 performing sales representatives."

"How many units of 'Product X' were sold in the 'North' region?"

# Trend Analysis
"What is the sales trend for 'Product Y' over the last six months?"

"Are sales increasing or decreasing for the 'Electronics' category?"

"Identify any seasonal sales patterns."

"Compare the sales performance of the 'East' and 'West' regions."

# Deeper Insights & Analysis
"What are the key drivers of our sales growth?"

"Which products are frequently sold together?" (Market Basket Analysis)

"Identify our most profitable customer segments."

"What is the average deal size for sales made by 'John Doe'?"

# Recommendations & Strategic Questions
"Based on recent trends, which products should we promote more aggressively?"

"Suggest a marketing strategy for our lowest-performing products."

"Which sales region needs more attention and resources?"

"Given the data, what are the top 3 opportunities for revenue growth next quarter?"

"Are there any underperforming products we should consider discontinuing?"
