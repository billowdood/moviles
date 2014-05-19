class CreateOrder < ActiveRecord::Migration
  def change
    create_table :orders do |t|
      t.integer :dish_id 
      t.integer :table_id
    end
  end
end
