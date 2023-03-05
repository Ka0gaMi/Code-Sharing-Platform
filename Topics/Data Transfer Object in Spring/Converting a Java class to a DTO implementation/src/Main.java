class Solution {
    ProductDTO convertProductToDTO(Product product) {
        ProductDTO dto = new ProductDTO(product.getId(), product.getModel(), product.getPrice());
        return dto;
    }
}