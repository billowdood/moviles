class CreateDish < ActiveRecord::Migration
  def change
    create_table :dishes do |t|
      t.float :price
    end
  end
end
