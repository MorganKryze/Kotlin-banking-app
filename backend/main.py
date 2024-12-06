import re

import pytesseract
from fastapi import APIRouter, File, HTTPException, UploadFile
from PIL import Image

router = APIRouter()


@router.post("/get_price")
async def get_price(file: UploadFile = File(...)) -> dict:
    """
    Extracts the price from an uploaded image.

    Parameters
    ----------
    - file : UploadFile
        The image file to process.

    Returns
    -------
    - dict: A dictionary containing the extracted prices.

    """
    # Ensure the uploaded file is an image
    if not file.content_type.startswith("image/"):
        raise HTTPException(status_code=400, detail="File must be an image.")

    try:
        # Open the image
        image = Image.open(file.file)

        # Perform OCR
        extracted_text = pytesseract.image_to_string(image)

        # Clean up the text
        lines = extracted_text.splitlines()
        useful_lines = [line.strip() for line in lines if line.strip()]

        # Define keywords to search for amounts
        keywords = ["MONTANT", "TOTAL", "TTC", "PRIX"]

        # Join lines for easier regex matching
        text = "\n".join(useful_lines)

        # Extract amounts after keywords
        prices = {}
        for keyword in keywords:
            match = re.search(
                rf"{keyword}\s*[:\-]?\s*([\d,.]+)\s*(EUR)?", text, re.IGNORECASE
            )
            if match:
                amount_str = match.group(1)  # Extract the amount
                currency = match.group(2) if match.group(2) else ""
                prices[keyword] = f"{amount_str} {currency}".strip()

        if not prices:
            raise HTTPException(
                status_code=404, detail="No price found in the image."
            )

        return {"prices": prices}

    except Exception as e:
        raise HTTPException(status_code=500, detail=f"An error occurred: {e!s}")
