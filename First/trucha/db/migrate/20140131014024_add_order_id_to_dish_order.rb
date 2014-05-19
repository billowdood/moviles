class AddOrderIdToDishOrder < ActiveRecord::Migration
  def change
    add_column :dish_orders, :order_id, :integer
  end
end
