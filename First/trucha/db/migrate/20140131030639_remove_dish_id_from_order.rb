class RemoveDishIdFromOrder < ActiveRecord::Migration
  def change
    remove_column :orders, :dish_id, :integer
  end
end
