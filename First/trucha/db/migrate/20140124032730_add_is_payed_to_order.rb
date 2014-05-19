class AddIsPayedToOrder < ActiveRecord::Migration
  def change
    add_column :orders, :is_payed, :boolean
  end
end
