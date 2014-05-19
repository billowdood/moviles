class CreateDishOrders < ActiveRecord::Migration
  def change
    create_table :dish_orders do |t|
      t.integer :table_id 
      t.integer :dish_id

      t.timestamps
    end
  end
end
