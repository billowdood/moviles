class RemoveTableIdFromDishOrder < ActiveRecord::Migration
  def change
    remove_column :dish_orders, :table_id, :integer
  end
end
