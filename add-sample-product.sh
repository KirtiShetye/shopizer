#!/bin/bash

# Add a sample product to Shopizer
# Usage: ./add-sample-product.sh

API_URL="http://localhost:8080/api/v2"
STORE="DEFAULT"
LANG="en"

# Product data
PRODUCT_JSON='{
  "productSpecifications": {
    "height": 10,
    "weight": 2,
    "width": 5,
    "length": 5,
    "dimensionUnitOfMeasure": "cm",
    "weightUnitOfMeasure": "kg"
  },
  "price": 99.99,
  "quantity": 100,
  "sku": "SAMPLE-001",
  "available": true,
  "visible": true,
  "dateAvailable": "2026-03-12",
  "sortOrder": 0,
  "productShipeable": true,
  "descriptions": [
    {
      "language": "en",
      "name": "Sample Product",
      "description": "This is a sample product for testing",
      "friendlyUrl": "sample-product",
      "title": "Sample Product"
    }
  ],
  "type": {
    "code": "GENERAL"
  },
  "categories": []
}'

echo "Adding sample product..."
curl -X POST "${API_URL}/private/product?store=${STORE}&lang=${LANG}" \
  -H "Content-Type: application/json" \
  -u "admin@shopizer.com:password" \
  -d "${PRODUCT_JSON}"

echo -e "\n\nProduct added! Check at: http://localhost:3000"
