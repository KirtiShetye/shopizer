#!/bin/bash

API="http://localhost:8080/api/v1"
STORE="DEFAULT"
LANG="en"
AUTH="admin@shopizer.com:password"

echo "Creating Electronics category..."
RESPONSE=$(curl -s -w "\n%{http_code}" --max-time 10 -X POST "${API}/private/category?store=${STORE}&lang=${LANG}" \
  -u "${AUTH}" \
  -H "Content-Type: application/json" \
  -d '{
    "code": "electronics",
    "sortOrder": 3,
    "visible": true,
    "descriptions": [{
      "language": "en",
      "name": "Electronics",
      "friendlyUrl": "electronics",
      "title": "Electronics"
    }]
  }')
HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
if [ "$HTTP_CODE" = "200" ] || [ "$HTTP_CODE" = "201" ]; then
  echo "✅ Electronics category created"
else
  echo "⚠️  Electronics category may already exist (HTTP $HTTP_CODE)"
fi

echo -e "\nCreating Laptops subcategory..."
RESPONSE=$(curl -s -w "\n%{http_code}" --max-time 10 -X POST "${API}/private/category?store=${STORE}&lang=${LANG}" \
  -u "${AUTH}" \
  -H "Content-Type: application/json" \
  -d '{
    "code": "laptops",
    "sortOrder": 1,
    "visible": true,
    "descriptions": [{
      "language": "en",
      "name": "Laptops",
      "friendlyUrl": "laptops",
      "title": "Laptops"
    }],
    "parent": {"code": "electronics"}
  }')
HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
if [ "$HTTP_CODE" = "200" ] || [ "$HTTP_CODE" = "201" ]; then
  echo "✅ Laptops category created"
else
  echo "⚠️  Laptops category may already exist (HTTP $HTTP_CODE)"
fi

echo -e "\nCreating Mobiles subcategory..."
RESPONSE=$(curl -s -w "\n%{http_code}" --max-time 10 -X POST "${API}/private/category?store=${STORE}&lang=${LANG}" \
  -u "${AUTH}" \
  -H "Content-Type: application/json" \
  -d '{
    "code": "mobiles",
    "sortOrder": 2,
    "visible": true,
    "descriptions": [{
      "language": "en",
      "name": "Mobile Phones",
      "friendlyUrl": "mobile-phones",
      "title": "Mobile Phones"
    }],
    "parent": {"code": "electronics"}
  }')
HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
if [ "$HTTP_CODE" = "200" ] || [ "$HTTP_CODE" = "201" ]; then
  echo "✅ Mobiles category created"
else
  echo "⚠️  Mobiles category may already exist (HTTP $HTTP_CODE)"
fi

echo -e "\n\nCreating Laptop products..."

# Laptop 1 - MacBook Pro
curl -s -X POST "${API}/private/product?store=${STORE}&lang=${LANG}" \
  -u "${AUTH}" \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "LAPTOP-001",
    "price": 1299.99,
    "quantity": 20,
    "available": true,
    "visible": true,
    "productShipeable": true,
    "descriptions": [{
      "language": "en",
      "name": "MacBook Pro 14-inch",
      "description": "Apple M2 Pro chip, 16GB RAM, 512GB SSD, Stunning Retina display",
      "friendlyUrl": "macbook-pro-14",
      "title": "MacBook Pro 14-inch"
    }],
    "type": "GENERAL",
    "categories": [{"code": "laptops"}],
    "inventory": {
      "quantity": 20,
      "price": {
        "price": 1299.99,
        "defaultPrice": true
      }
    }
  }'

echo ""

# Laptop 2 - Dell XPS
curl -s -X POST "${API}/private/product?store=${STORE}&lang=${LANG}" \
  -u "${AUTH}" \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "LAPTOP-002",
    "price": 999.99,
    "quantity": 30,
    "available": true,
    "visible": true,
    "productShipeable": true,
    "descriptions": [{
      "language": "en",
      "name": "Dell XPS 13",
      "description": "Intel Core i7, 16GB RAM, 512GB SSD, InfinityEdge display",
      "friendlyUrl": "dell-xps-13",
      "title": "Dell XPS 13"
    }],
    "type": "GENERAL",
    "categories": [{"code": "laptops"}],
    "inventory": {
      "quantity": 30,
      "price": {
        "price": 999.99,
        "defaultPrice": true
      }
    }
  }'

echo ""

# Laptop 3 - HP Pavilion
curl -s -X POST "${API}/private/product?store=${STORE}&lang=${LANG}" \
  -u "${AUTH}" \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "LAPTOP-003",
    "price": 699.99,
    "quantity": 40,
    "available": true,
    "visible": true,
    "productShipeable": true,
    "descriptions": [{
      "language": "en",
      "name": "HP Pavilion 15",
      "description": "AMD Ryzen 5, 8GB RAM, 256GB SSD, Full HD display",
      "friendlyUrl": "hp-pavilion-15",
      "title": "HP Pavilion 15"
    }],
    "type": "GENERAL",
    "categories": [{"code": "laptops"}],
    "inventory": {
      "quantity": 40,
      "price": {
        "price": 699.99,
        "defaultPrice": true
      }
    }
  }'

echo -e "\n\nCreating Mobile products..."

# Mobile 1 - iPhone
curl -s -X POST "${API}/private/product?store=${STORE}&lang=${LANG}" \
  -u "${AUTH}" \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "MOBILE-001",
    "price": 999.99,
    "quantity": 50,
    "available": true,
    "visible": true,
    "productShipeable": true,
    "descriptions": [{
      "language": "en",
      "name": "iPhone 14 Pro",
      "description": "6.1-inch display, A16 Bionic chip, 128GB, Pro camera system",
      "friendlyUrl": "iphone-14-pro",
      "title": "iPhone 14 Pro"
    }],
    "type": "GENERAL",
    "categories": [{"code": "mobiles"}],
    "inventory": {
      "quantity": 50,
      "price": {
        "price": 999.99,
        "defaultPrice": true
      }
    }
  }'

echo ""

# Mobile 2 - Samsung Galaxy
curl -s -X POST "${API}/private/product?store=${STORE}&lang=${LANG}" \
  -u "${AUTH}" \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "MOBILE-002",
    "price": 849.99,
    "quantity": 60,
    "available": true,
    "visible": true,
    "productShipeable": true,
    "descriptions": [{
      "language": "en",
      "name": "Samsung Galaxy S23",
      "description": "6.1-inch AMOLED, Snapdragon 8 Gen 2, 256GB, 50MP camera",
      "friendlyUrl": "samsung-galaxy-s23",
      "title": "Samsung Galaxy S23"
    }],
    "type": "GENERAL",
    "categories": [{"code": "mobiles"}],
    "inventory": {
      "quantity": 60,
      "price": {
        "price": 849.99,
        "defaultPrice": true
      }
    }
  }'

echo ""

# Mobile 3 - Google Pixel
curl -s -X POST "${API}/private/product?store=${STORE}&lang=${LANG}" \
  -u "${AUTH}" \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "MOBILE-003",
    "price": 599.99,
    "quantity": 45,
    "available": true,
    "visible": true,
    "productShipeable": true,
    "descriptions": [{
      "language": "en",
      "name": "Google Pixel 7",
      "description": "6.3-inch OLED, Google Tensor G2, 128GB, Advanced AI camera",
      "friendlyUrl": "google-pixel-7",
      "title": "Google Pixel 7"
    }],
    "type": "GENERAL",
    "categories": [{"code": "mobiles"}],
    "inventory": {
      "quantity": 45,
      "price": {
        "price": 599.99,
        "defaultPrice": true
      }
    }
  }'

echo ""

# Mobile 4 - OnePlus
curl -s -X POST "${API}/private/product?store=${STORE}&lang=${LANG}" \
  -u "${AUTH}" \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "MOBILE-004",
    "price": 449.99,
    "quantity": 55,
    "available": true,
    "visible": true,
    "productShipeable": true,
    "descriptions": [{
      "language": "en",
      "name": "OnePlus 11",
      "description": "6.7-inch AMOLED, Snapdragon 8 Gen 2, 256GB, 100W fast charging",
      "friendlyUrl": "oneplus-11",
      "title": "OnePlus 11"
    }],
    "type": "GENERAL",
    "categories": [{"code": "mobiles"}],
    "inventory": {
      "quantity": 55,
      "price": {
        "price": 449.99,
        "defaultPrice": true
      }
    }
  }'

echo -e "\n\n✅ Electronics products added successfully!"
echo "📱 Added 4 Mobile Phones"
echo "💻 Added 3 Laptops"
echo ""
echo "View products at:"
echo "- Frontend: http://localhost:3000"
echo "- Admin: http://localhost:4200"
echo "- API: http://localhost:8080/api/v1/products?store=DEFAULT&lang=en"
